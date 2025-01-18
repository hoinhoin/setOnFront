package com.example.seton.data

data class User(
    val userId: Long,
    val name: String,
    val profileImageUrl: String
)

data class House(
    val houseId: Long,
    val user: User,
    val title: String,
    val thumbnailUrl: String,
    val imageUrls: List<String>,
    val phoneNumber: String,
    val address: String,
    val size: Int,
    val description: String,
    val price: Int,
    val likeCount: Int,
    val tags: List<String>
)

data class ApiResponse(
    val code: String,
    val message: String,
    val status: Int,
    val timestamp: String
)