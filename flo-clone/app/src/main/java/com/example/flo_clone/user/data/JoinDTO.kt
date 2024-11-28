package com.example.flo_clone.user.data

data class JoinRequestDTO(
    val name: String,
    val email: String,
    val password: String
)

data class JoinSuccessDTO(
    val memberId: Int,
    val createdAt: String,
    val updatedAt: String
)
