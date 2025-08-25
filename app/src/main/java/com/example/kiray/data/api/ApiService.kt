package com.example.kiray.data.api

import com.example.kiray.data.model.request.EditProfileRequest
import com.example.kiray.data.model.request.ProfileRequest
import com.example.kiray.data.model.request.house.HouseRequest
import com.example.kiray.data.repository.ApiResponse
import com.example.kiray.model.House
import com.example.kiray.model.Marker
import com.example.kiray.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface ApiService {
    @GET("marker")
    suspend fun getMarkers(): List<Marker>

    @POST("user/otp")
    suspend fun requestOtp(@Body user: User): UserResponse

    @POST("user/signUp")
    suspend fun registerUser(@Body data: SignUpRequest): UserResponse

    @POST("user/signIn")
    suspend fun loginUser(@Body user: SignInRequest): UserResponse

    @POST("user/update")
    suspend fun updateUser(@Body user: EditProfileRequest): User

    @GET("user/profile")
    suspend fun getProfile(): User

    @GET("house")
    suspend fun getHouses(): List<House>

    @DELETE("house/{id}")
    suspend fun deleteHouse(@Path("id") id: String?): Boolean

    @POST("house")
    suspend fun addHouse(@Body request: HouseRequest): House

    @PUT("house/{id}")
    suspend fun editHouse(@Path("id") id: String?, @Body request: HouseRequest): House
}



data class UserResponse(
    val token:String?,
    val success : Boolean,
    val message : String
)
data class SignInRequest(
    val email:String,
    val password:String
)

data class SignUpRequest(
     val user:User?,
     val otp:Int
)

data class ExtendedApiResponse(
    val success:Boolean,
    val message:String
)
