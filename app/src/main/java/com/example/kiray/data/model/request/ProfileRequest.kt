package com.example.kiray.data.model.request

data class ProfileRequest(
    val id: String?
)

data class EditProfileRequest(
    val name: String?,
    val email: String?,
    val phone: String?,
    val address: String?
)