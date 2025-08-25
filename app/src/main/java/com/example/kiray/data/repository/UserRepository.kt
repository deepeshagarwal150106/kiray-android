package com.example.kiray.data.repository

import com.example.kiray.data.api.ApiService
import com.example.kiray.data.api.SignInRequest
import com.example.kiray.data.api.SignUpRequest
import com.example.kiray.data.api.UserResponse
import com.example.kiray.data.local.TokenStorage
import com.example.kiray.data.model.request.EditProfileRequest
import com.example.kiray.data.model.request.ProfileRequest
import com.example.kiray.model.User
import javax.inject.Inject

class UserRepository@Inject constructor(
    private val api: ApiService,
    private val tokenStorage: TokenStorage
) {
    suspend fun requestOtp(user: User): UserResponse{
        return api.requestOtp(user)
    }

    suspend fun registerUser(data: SignUpRequest): UserResponse{
        return api.registerUser(data)
    }

    suspend fun loginUser(user: SignInRequest): UserResponse{
        return api.loginUser(user)
    }

    suspend fun getProfile(): User {
        return api.getProfile()
    }

    fun saveJwt(token: String): ApiResponse{
        try {
            tokenStorage.saveJwt(token)
            return ApiResponse(success = true)
        }
        catch (e: Exception){
            e.printStackTrace()
            return ApiResponse(success = false)
        }
    }

    fun getJwt(): String? {
        return tokenStorage.getJwt()
    }

    fun clearJwt(){
        tokenStorage.clearJwt()
    }

    fun logout(){
        tokenStorage.clearJwt()
    }

    suspend fun updateUser(data: EditProfileRequest): User{
        return api.updateUser(data)
    }
}

data class ApiResponse(
    val success: Boolean
)