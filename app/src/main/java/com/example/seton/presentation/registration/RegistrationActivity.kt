package com.example.seton.presentation.registration

import android.net.Uri
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seton.R
import com.example.seton.databinding.ActivityRegistrationBinding
import com.google.android.material.chip.Chip

class RegistrationActivity : AppCompatActivity() {

    private val binding: ActivityRegistrationBinding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }

    private val selectedImages = mutableListOf<Uri>()
    private lateinit var adapter: InnerPhotoAdapter

    // ActivityResultLauncher를 onCreate 이전에 선언
    private val photoPickerLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                selectedImages.add(it)
                adapter.notifyDataSetChanged()
            }
        }

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

        binding.viewAddInnerPhoto.setOnClickListener {
            if (selectedImages.size < 5) {
                // 준비된 Launcher를 사용하여 포토 피커 실행
                photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            } else {
                Toast.makeText(this, "최대 5개의 이미지만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
