package com.example.seton.presentation.housedetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil3.load
import com.example.seton.R
import com.example.seton.data.House
import com.example.seton.databinding.ActivityHouseDetailBinding
import com.example.seton.di.RetrofitInstance
import com.example.seton.presentation.registration.InnerPhotoAdapter
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class HouseDetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHouseDetailBinding.inflate(layoutInflater) }
    private lateinit var innerPhotoAdapter: DetailInnerPhotoAdapter
    private var currentLikeCount = 0
    private var isLiked = false
    private var currentHouseId: Long = -1
    private var currentPhoneNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.house_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // 툴바 설정
        setSupportActionBar(binding.toolbarHouseDetail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarHouseDetail.setNavigationOnClickListener { finish() }

        // 리사이클러뷰 설정
        innerPhotoAdapter = DetailInnerPhotoAdapter()
        binding.rvInnerPhotos.apply {
            adapter = innerPhotoAdapter
            layoutManager = LinearLayoutManager(this@HouseDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.ivBookmark.setOnClickListener {
            toggleLike()
        }

        binding.btnContact.setOnClickListener {
            makePhoneCall()
        }

        // 데이터 로드
        val houseId = intent.getLongExtra("houseId", -1)
        if (houseId != -1L) {
            loadHouseDetail(houseId)
        } else {
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun loadHouseDetail(houseId: Long) {
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.getApiService(this@HouseDetailActivity)
                    .getHouseDetail(houseId)

                if (response.isSuccessful) {
                    response.body()?.let { house ->
                        updateUI(house)
                    }
                } else {
                    Toast.makeText(this@HouseDetailActivity, "데이터를 불러오는데 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@HouseDetailActivity, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(house: House) {
        with(binding) {
            currentHouseId = house.houseId
            currentLikeCount = house.likeCount
            // 썸네일 이미지 로드
            ivHouseDetail.load(house.thumbnailUrl)

            // 텍스트 정보 설정
            tvHouseDetailTitle.text = house.title
            tvHouseDetailLocation.text = house.address
            tvHouseDetailBookmarkCount.text = house.likeCount.toString()
            tvHouseDetailUserName.text = house.user.name
            tvHouseDetailSize.text = "평수 약 ${house.size}평"
            tvHouseDetailPrice.text = "가격: ${house.price}원"
            tvHouseDetailContent.text = house.description
            currentPhoneNumber = house.phoneNumber

            // 좋아요 아이콘 상태 업데이트 (이미지 리소스는 프로젝트에 맞게 수정)
            ivBookmark.setImageResource(
                if (isLiked) R.drawable.ic_bookmark_filled
                else R.drawable.ic_detail_bookmark
            )
            // 프로필 이미지 로드
            ivHouseDetailUserProfile.load(house.user.profileImageUrl ?: R.drawable.ic_launcher_background)

            // 태그 추가
            chipGroupKeyword.removeAllViews()
            house.tags.forEach { tag ->
                val chip = Chip(this@HouseDetailActivity).apply {
                    text = tag
                    isClickable = false
                }
                chipGroupKeyword.addView(chip)
            }

            // 내부 사진 설정
            innerPhotoAdapter.submitList(house.imageUrls)
        }
    }

    private fun toggleLike() {
        if (currentHouseId == -1L) return

        // 즉시 UI 업데이트
        isLiked = !isLiked
        currentLikeCount += if (isLiked) 1 else -1

        // UI 갱신
        binding.tvHouseDetailBookmarkCount.text = currentLikeCount.toString()
        binding.ivBookmark.setImageResource(
            if (isLiked) R.drawable.ic_bookmark_filled
            else R.drawable.ic_detail_bookmark
        )

        // 서버에 요청 보내기
        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.getApiService(this@HouseDetailActivity)
                    .toggleLike(currentHouseId)

                if (!response.isSuccessful) {
                    // UI 복구
                    revertLikeState()
                    Toast.makeText(
                        this@HouseDetailActivity,
                        "작업을 완료할 수 없습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                // UI 복구
                revertLikeState()
                Toast.makeText(
                    this@HouseDetailActivity,
                    "네트워크 오류가 발생했습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // UI 상태를 되돌리는 함수 분리
    private fun revertLikeState() {
        isLiked = !isLiked
        currentLikeCount += if (isLiked) 1 else -1
        binding.tvHouseDetailBookmarkCount.text = currentLikeCount.toString()
        binding.ivBookmark.setImageResource(
            if (isLiked) R.drawable.ic_bookmark_filled
            else R.drawable.ic_detail_bookmark
        )
    }


    private fun makePhoneCall() {
        if (currentPhoneNumber.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$currentPhoneNumber")
            }
            try {
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "전화를 걸 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "전화번호가 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}