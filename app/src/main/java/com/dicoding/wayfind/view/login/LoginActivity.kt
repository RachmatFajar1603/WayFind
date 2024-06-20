package com.dicoding.wayfind.view.login

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.wayfind.AppPreferences
import com.dicoding.wayfind.R
import com.dicoding.wayfind.data.retrofit.ApiConfig
import com.dicoding.wayfind.databinding.ActivityLoginBinding
import com.dicoding.wayfind.view.home.HomeActivity
import com.dicoding.wayfind.view.register.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LoginActivity", "onCreate called")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appPreferences = AppPreferences(this)

//        loginViewModel.message.observe(this) {
//            val user = loginViewModel.userlogin.value
//            checkLoginUser(it, loginViewModel.isError, user?.loginResult?.token, appPreferences)
//        }


        if (appPreferences.isLoggedIn) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.isEmailValid && binding.etPass.isPasswordValid) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPass.text.toString()
                loginViewModel.getLoginUser(email, password, "token")
            }
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        setupView()

        loginViewModel.message.observe(this) {
            val user = loginViewModel.userlogin.value
            checkLoginUser(it, loginViewModel.isError, user?.token, appPreferences)
        }

    }

    private fun checkLoginUser(message: String, isError: Boolean, token: String?, preference: AppPreferences) {
        if (!isError && message == "User logged in successfully") {
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
            ).show()
            preference.isLoggedIn = true
            if (token != null) preference.authToken = token

            // Fetch user data
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val user = apiService.getUser("Bearer " + token)

                    withContext(Dispatchers.Main) {
                        // Save user data to SharedPreferences
                        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                        with (sharedPref.edit()) {
                            putInt("age", user.age)
                            putString("gender", user.gender)
                            putString("name", user.name)
                            putString("email", user.email)
                            apply()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        // Handle the exception
                        Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Handle error cases...
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}