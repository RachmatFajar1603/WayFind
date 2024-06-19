package com.dicoding.wayfind.view.profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.wayfind.AppPreferences
import com.dicoding.wayfind.R
import com.dicoding.wayfind.view.favorite.FavoriteActivity
import com.dicoding.wayfind.view.home.HomeActivity
import com.dicoding.wayfind.view.login.LoginActivity
import com.dicoding.wayfind.view.map.TurnByTurnExperienceActivity
import nl.joery.animatedbottombar.AnimatedBottomBar

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupView()

        val backButton = findViewById<ImageButton>(R.id.back_button_profile)
        backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()  // Optional: if you want to remove this activity from the stack
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        val bottomBar = findViewById<AnimatedBottomBar>(R.id.bottom_bar)

        bottomBar.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                if (newTab.id == R.id.tab_maps) {
                    val intent = Intent(this@ProfileActivity, TurnByTurnExperienceActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (newTab.id == R.id.tab_profile) {
                    val intent = Intent(this@ProfileActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (newTab.id == R.id.tab_home) {
                    val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (newTab.id == R.id.tab_favorite) {
                    val intent = Intent(this@ProfileActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                // Handle tab reselection if needed
            }
        })

        val logoutButton = findViewById<ImageButton>(R.id.logout_button)
        logoutButton.setOnClickListener {
            // Clear user session data
            val appPreferences = AppPreferences(this)
            appPreferences.clearUserSession()

            // Navigate back to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        val appPreferences = AppPreferences(this)

        val tvFullName = findViewById<TextView>(R.id.tv_name_value)
        val tvEmail = findViewById<TextView>(R.id.tv_name_email)
        val tvAge = findViewById<TextView>(R.id.tv_name_age)
        val tvGender = findViewById<TextView>(R.id.tv_gender_value)

        tvFullName.text = appPreferences.fullName
        tvEmail.text = appPreferences.email
        tvAge.text = appPreferences.age.toString()
        tvGender.text = appPreferences.gender
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