package com.dicoding.wayfind.view.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.dicoding.wayfind.databinding.ActivityLoginBinding
import com.dicoding.wayfind.view.home.HomeActivity
import com.dicoding.wayfind.view.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appPreferences = AppPreferences(this)

        loginViewModel.message.observe(this) {
            val user = loginViewModel.userlogin.value
            checkLoginUser(it, loginViewModel.isError, user?.loginResult?.token, appPreferences)
        }


        if (appPreferences.isLoggedIn) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.isEmailValid && binding.etPass.isPasswordValid) {
                val email = binding.etEmail.text.toString()
                val password = binding.etPass.text.toString()
                loginViewModel.getLoginUser(email, password)
            }
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }


    }

    private fun checkLoginUser(message: String, isError: Boolean, token: String?, preference: AppPreferences) {
        if (!isError) {
            Toast.makeText(
                this,
                message,
                Toast.LENGTH_LONG
            ).show()
            preference.isLoggedIn = true
            if (token != null) preference.authToken = token
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            when (message) {
                "Unauthorized" -> {
                    Toast.makeText(this, getString(R.string.unauthorized), Toast.LENGTH_SHORT)
                        .show()
                    binding.etEmail.apply {
                        setText("")
                        requestFocus()
                    }
                    binding.etPass.setText("")
                }
                "timeout" -> {
                    Toast.makeText(this, getString(R.string.timeout), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(
                        this,
                        "${getString(R.string.error_message)} $message",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
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