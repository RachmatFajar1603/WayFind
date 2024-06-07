package com.dicoding.wayfind

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreferences(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit { putBoolean(IS_LOGGED_IN, value) }

    var authToken: String?
        get() = sharedPreferences.getString(AUTH_TOKEN, null)
        set(value) = sharedPreferences.edit { putString(AUTH_TOKEN, value) }

    companion object {
        const val PREFS_NAME = "MyAppPreferences"
        const val IS_LOGGED_IN = "isLoggedIn"
        const val AUTH_TOKEN = "authToken"
    }
}
