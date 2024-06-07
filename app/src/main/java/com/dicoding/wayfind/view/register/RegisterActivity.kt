package com.dicoding.wayfind.view.register

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
import com.dicoding.wayfind.R
import com.dicoding.wayfind.databinding.ActivityRegisterBinding
import com.dicoding.wayfind.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        registerViewModel.message.observe(this) {
            checkRegisterUser(it, registerViewModel.isError)
        }


        binding.btnRegister.setOnClickListener {
            if (binding.etEmail.isEmailValid && binding.etPass.isPasswordValid) {
                val name = binding.etName.text.toString()
                val email = binding.etEmail.text.toString()
                val pass = binding.etPass.text.toString()

                if (name.isEmpty()) {
                    binding.etName.error = getString(R.string.input_name)
                    return@setOnClickListener
                }

                registerViewModel.getRegisterUser(name, email, pass)
            }
        }
    }

    private fun checkRegisterUser(message: String, isError: Boolean) {
        if (!isError) {
            Toast.makeText(this, getString(R.string.user_create), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            when (message) {
                "Bad Request" -> {
                    Toast.makeText(this, getString(R.string.email_taken), Toast.LENGTH_SHORT).show()
                    binding.etEmail.apply {
                        setText("")
                        requestFocus()
                    }
                }
                "timeout" -> {
                    Toast.makeText(this, getString(R.string.timeout), Toast.LENGTH_SHORT).show()
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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