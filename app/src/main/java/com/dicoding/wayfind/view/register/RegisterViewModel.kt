package com.dicoding.wayfind.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.wayfind.data.response.RegisterResponse
import com.dicoding.wayfind.data.retrofit.ApiConfig
import com.dicoding.wayfind.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var isError: Boolean = false

    fun getRegisterUser(name: String, email: String, password: String, age: Int, gender: String, message: String) {
        _isLoading.value = true
        val request = ApiService.RegisterRequest(name, email, password, age, gender, message)
        val api = ApiConfig.getApiService().register(request)
        api.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody?.message == "User registered successfully") {
                    isError = false
                    _message.value = responseBody.message
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                isError = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }
}