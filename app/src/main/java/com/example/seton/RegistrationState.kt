package com.example.seton

data class RegistrationState(
    val title: String,
    val content: String,
) {
    companion object {

        fun empty(
            title: String = "",
            content: String = "",
        ) = RegistrationState(
            title = title,
            content = content,
        )
    }
}