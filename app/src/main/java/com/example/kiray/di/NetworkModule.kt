package com.example.kiray.di

import com.example.kiray.BuildConfig
import com.example.kiray.data.api.ApiService
import com.example.kiray.data.local.TokenStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



class AuthInterceptor(private val tokenProvider: suspend () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { tokenProvider() } // read token
        val newRequest = chain.request().newBuilder().apply {
            if (!(token.isNullOrEmpty() || token=="null")) {
                addHeader("Authorization", "Bearer $token")
            }
        }.build()
        return chain.proceed(newRequest)
    }
}


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkkHttpClient(tokenStorage: TokenStorage): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor { tokenStorage.getJwt() })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client : OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BACKEND_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService=
        retrofit.create(ApiService::class.java)
}
