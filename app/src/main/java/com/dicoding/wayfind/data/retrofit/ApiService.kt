package com.dicoding.wayfind.data.retrofit

import com.dicoding.wayfind.data.response.LoginResponse
import com.dicoding.wayfind.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    data class LoginRequest(
        val email: String,
        val password: String,
        val token: String
    )

    data class RegisterRequest(
        val name: String,
        val email: String,
        val password: String,
        val age: Int,
        val gender: String,
        val message: String
    )

    data class LogoutRequest(
        val email: String,
        val password: String,
        val token: String
    )

    @POST("api/auth/register")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @POST("api/auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("api/auth/logout")
    fun logout(
        @Body request: LogoutRequest
    ): Call<LoginResponse>
}