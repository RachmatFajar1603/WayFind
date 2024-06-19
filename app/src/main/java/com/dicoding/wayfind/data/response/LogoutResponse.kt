package com.dicoding.wayfind.data.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String
)
