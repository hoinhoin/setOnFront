package com.example.seton.presentation.registration

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seton.R
import com.example.seton.databinding.ActivityRegistrationBinding
import com.example.seton.di.RetrofitInstance
import com.example.seton.di.createPartFromString
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class RegistrationActivity : AppCompatActivity() {

    private val binding: ActivityRegistrationBinding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }

    private val selectedImages = mutableListOf<Uri>() // 내부 사진 URI 리스트
    private var selectedThumbnailUri: Uri? = null // 썸네일 URI
    private lateinit var adapter: InnerPhotoAdapter

    // ActivityResultLauncher를 onCreate 이전에 선언
    private val photoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                if (isSelectingThumbnail) {
                    // 썸네일 선택 시 처리
                    selectedThumbnailUri = it
                    binding.ivCamera.setImageURI(it) // 썸네일 이미지뷰에 미리 보기
                } else {
                    // 내부 사진 선택 시 처리
                    if (selectedImages.size < 5) {
                        selectedImages.add(it)
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this, "최대 5개의 이미지만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    private var isSelectingThumbnail = false // 썸네일 선택 여부 플래그

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registration)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        adapter = InnerPhotoAdapter(selectedImages)
        binding.rvInnerPhotos.adapter = adapter
        binding.rvInnerPhotos.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        // 썸네일 선택 버튼 클릭 시
        binding.ivCamera.setOnClickListener {
            isSelectingThumbnail = true // 썸네일 선택 모드 활성화
            photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // 내부 사진 추가 버튼 클릭 시
        binding.viewAddInnerPhoto.setOnClickListener {
            isSelectingThumbnail = false // 내부 사진 선택 모드 활성화
            if (selectedImages.size < 5) {
                if (selectedImages.size > 0) {
                    binding.rvInnerPhotos.visibility = RecyclerView.VISIBLE
                }
                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                Toast.makeText(this, "최대 5개의 이미지만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 등록 버튼 클릭 시 데이터 업로드
        binding.btnRegistration.setOnClickListener {
            if (validateInputs()) {
                uploadData()
            } else {
                Toast.makeText(this, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputs(): Boolean {
        return binding.etInputRegistrationTitle.text.isNotEmpty() &&
                binding.etInputRegistrationPhoneNumber.text.isNotEmpty() &&
                binding.etInputRegistrationLocation.text.isNotEmpty() &&
                binding.etInputRegistrationSize.text.isNotEmpty() &&
                binding.etInputRegistrationDetail.text.isNotEmpty() &&
                binding.etInputRegistrationPrice.text.isNotEmpty() &&
                selectedThumbnailUri != null
    }

    private fun uploadData() {
        val context = this

        // 문자열 데이터를 RequestBody로 변환
        val title = createPartFromString(binding.etInputRegistrationTitle.text.toString())
        val phoneNumber = createPartFromString(binding.etInputRegistrationPhoneNumber.text.toString())
        val address = createPartFromString(binding.etInputRegistrationLocation.text.toString())
        val size = createPartFromString(binding.etInputRegistrationSize.text.toString())
        val description = createPartFromString(binding.etInputRegistrationDetail.text.toString())
        val price = createPartFromString(binding.etInputRegistrationPrice.text.toString())
        val tags = createPartFromString("[\"tag1\", \"tag2\"]") // JSON 형식의 태그 데이터 예시

        // 썸네일 파일 준비
        val thumbnailPart = selectedThumbnailUri?.let {
            prepareFilePart(context, "thumbnail", it)
        }

        if (thumbnailPart == null) {
            Toast.makeText(this, "썸네일을 선택해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // 내부 사진 파일 준비
        val imageParts = selectedImages.map {
            prepareFilePart(context, "images", it)
        }

        // Retrofit API 호출
        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.getApiService(context)
                val response = apiService.uploadData(
                    title = title,
                    phoneNumber = phoneNumber,
                    address = address,
                    size = size,
                    description = description,
                    price = price,
                    tags = tags,
                    thumbnail = thumbnailPart,
                    images = imageParts
                )
                Log.d("Upload", "Upload successful: $response")
                Toast.makeText(context, "등록 완료!", Toast.LENGTH_SHORT).show()
                finish() // 업로드 성공 후 액티비티 종료
            } catch (e: Exception) {
                Log.e("Upload", "Upload failed", e)
                Toast.makeText(context, "등록 실패!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
fun prepareFilePart(context: Context, partName: String, fileUri: Uri): MultipartBody.Part {
    val contentResolver: ContentResolver = context.contentResolver
    val fileName = getFileName(context, fileUri)

    // 임시 파일 생성
    val tempFile = File(context.cacheDir, fileName ?: "temp_file")
    val inputStream: InputStream? = contentResolver.openInputStream(fileUri)
    val outputStream = FileOutputStream(tempFile)

    // InputStream 데이터를 임시 파일에 복사
    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    // 파일을 RequestBody로 변환
    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), tempFile)

    return MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
}

private fun getFileName(context: Context, fileUri: Uri): String? {
    val cursor = context.contentResolver.query(fileUri, null, null, null, null)
    var name: String? = null
    cursor?.use {
        if (it.moveToFirst()) {
            name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
        }
    }
    return name
}

