package com.dicoding.wayfind.view.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.wayfind.R
import com.dicoding.wayfind.adapter.ListPlacesAdapter
import com.dicoding.wayfind.darkmode.SettingPreferences
import com.dicoding.wayfind.darkmode.ViewModelFactory
import com.dicoding.wayfind.darkmode.dataStore
import com.dicoding.wayfind.data.Places
import com.dicoding.wayfind.view.favorite.FavoriteActivity
import com.dicoding.wayfind.view.googlemaps.GoogleMapsActivity
import com.dicoding.wayfind.view.map.MapsActivity
import com.dicoding.wayfind.view.map.TurnByTurnExperienceActivity
import com.dicoding.wayfind.view.profile.ProfileActivity
import nl.joery.animatedbottombar.AnimatedBottomBar

class HomeActivity : AppCompatActivity() {

    private lateinit var rvPlaces: RecyclerView
    private val list = ArrayList<Places>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupView()

        val switchTheme = findViewById<ImageButton>(R.id.switcher)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val homeViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            HomeViewModel::class.java
        )
        homeViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.setImageResource(R.drawable.dark_mode_on)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.setImageResource(R.drawable.dark_mode_off)
            }
        }

        switchTheme.setOnClickListener {
            val isDarkModeActive = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                Configuration.UI_MODE_NIGHT_YES -> false
                else -> true
            }
            homeViewModel.saveThemeSetting(isDarkModeActive)
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
                    val intent = Intent(this@HomeActivity, TurnByTurnExperienceActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (newTab.id == R.id.tab_profile) {
                    val intent = Intent(this@HomeActivity, ProfileActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (newTab.id == R.id.tab_home) {
                    val intent = Intent(this@HomeActivity, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (newTab.id == R.id.tab_favorite) {
                    val intent = Intent(this@HomeActivity, FavoriteActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
            }

            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                // Handle tab reselection if needed
            }
        })

        rvPlaces = findViewById(R.id.rv_places)
        rvPlaces.setHasFixedSize(true)

        list.addAll(getListPlaces())
        showRecyclerList()
    }

    private fun getListPlaces(): ArrayList<Places> {
        val dataPhoto = resources.getStringArray(R.array.data_photo)
        val dataName = resources.getStringArray(R.array.data_name)
        val dataRating = resources.getStringArray(R.array.data_rating)
        val dataTime = resources.getStringArray(R.array.data_time)
        val dataLocation = resources.getStringArray(R.array.data_location)
        val dataPrice = resources.getStringArray(R.array.data_price)
        val dataCoordinate = resources.getStringArray(R.array.data_coordinate)

        // Ensure all arrays have the same length
        val minLength = minOf(dataPhoto.size,dataName.size, dataRating.size, dataTime.size, dataLocation.size, dataPrice.size, dataCoordinate.size)

        val listPlaces = ArrayList<Places>()
        for (i in 0 until minLength) {
            val places = Places(dataPhoto[i],dataName[i], dataRating[i], dataTime[i], dataLocation[i], dataPrice[i], dataCoordinate[i])
            listPlaces.add(places)
        }
        return listPlaces
    }

    private fun showRecyclerList() {
        rvPlaces.layoutManager = LinearLayoutManager(this)
        val listPlacesAdapter = ListPlacesAdapter(list)
        rvPlaces.adapter = listPlacesAdapter
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