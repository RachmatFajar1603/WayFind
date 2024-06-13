package com.dicoding.wayfind.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Places(
    val photo: String,
    var name: String,
    val rating: String,
    val time: String,
    val location: String,
    val price: String
) : Parcelable
