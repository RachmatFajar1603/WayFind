package com.dicoding.wayfind.view.favorite

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.wayfind.R
import com.dicoding.wayfind.view.home.HomeActivity
import com.dicoding.wayfind.view.map.TurnByTurnExperienceActivity
import com.dicoding.wayfind.view.profile.ProfileActivity
import nl.joery.animatedbottombar.AnimatedBottomBar

class FavoriteActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setupView()

        val backButton = findViewById<ImageButton>(R.id.back_button_favorite)
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
                    val intent = Intent(this@FavoriteActivity, TurnByTurnExperienceActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (newTab.id == R.id.tab_profile) {
                    val intent = Intent(this@FavoriteActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (newTab.id == R.id.tab_home) {
                    val intent = Intent(this@FavoriteActivity, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (newTab.id == R.id.tab_favorite) {
                    val intent = Intent(this@FavoriteActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                // Handle tab reselection if needed
            }
        })
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