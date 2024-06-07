package com.dicoding.wayfind.view.map

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView

class MapsActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create a map programmatically and set the initial camera
        mapView = MapView(this)
        mapView.mapboxMap.setCamera(
            CameraOptions.Builder()
                .center(Point.fromLngLat(-4.421182478253528, 122.15674150646637))
                .pitch(60.0)
                .padding(EdgeInsets(40.0,5.0,80.0,5.0))
                .zoom(15.5)
                .bearing(-17.6)
                .build()
        )
        // Loads Mapbox Standard Style by default.
        mapView.mapboxMap.getStyle { style ->
            // default style is loaded
        }

        // Add the map view to the activity (you can also add it to other views as a child)
        setContentView(mapView)


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