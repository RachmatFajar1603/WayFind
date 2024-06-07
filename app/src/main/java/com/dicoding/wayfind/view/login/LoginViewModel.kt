package com.dicoding.wayfind.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.wayfind.data.response.LoginResponse
import com.dicoding.wayfind.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userLogin = MutableLiveData<LoginResponse>()
    val userlogin: LiveData<LoginResponse> = _userLogin

    var isError: Boolean = false

    fun getLoginUser(email: String, password: String) {
        _isLoading.value = true
        val api = ApiConfig.getApiService().login(email, password)
        api.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful) {
                    isError = false
                    _userLogin.value = responseBody!!
                    _message.value = "Login as ${_userLogin.value!!.loginResult.name}"
                } else {
                    isError = true
                    _message.value = response.message()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isError = true
                _isLoading.value = false
                _message.value = t.message.toString()
            }
        })
    }
}