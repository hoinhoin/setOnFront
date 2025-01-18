package com.example.seton.presentation.houseregistration

data class RegistrationState(
    val title: String = "",
    val content: String = "",
    val location: String = "",
    val startArea: String = "",
    val endArea: String = "",
    val price: String = "",
    val phoneNumber: String = "",
    val tags: List<String> = emptyList()
) {
    companion object {
        fun empty() = RegistrationState()
    }
}