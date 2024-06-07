package com.dicoding.wayfind.data.response

import com.google.gson.annotations.SerializedName

data class UserLoginResponse(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)