package com.example.flo_clone.user.data

data class LoginRequestDTO(
    val email: String,
    val password: String
)

data class LoginSuccessDTO(
    val memberId: Int,
    val accessToken: String
)
