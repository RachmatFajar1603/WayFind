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

    fun clearUserSession() {
        sharedPreferences.edit {
            remove(IS_LOGGED_IN)
            remove(AUTH_TOKEN)
        }
    }

    var fullName: String?
        get() = sharedPreferences.getString(FULL_NAME, null)
        set(value) = sharedPreferences.edit { putString(FULL_NAME, value) }

    var email: String?
        get() = sharedPreferences.getString(EMAIL, null)
        set(value) = sharedPreferences.edit { putString(EMAIL, value) }

    var age: Int
        get() = sharedPreferences.getInt(AGE, 0)
        set(value) = sharedPreferences.edit { putInt(AGE, value) }

    var gender: String?
        get() = sharedPreferences.getString(GENDER, null)
        set(value) = sharedPreferences.edit { putString(GENDER, value) }



    companion object {
        const val PREFS_NAME = "MyAppPreferences"
        const val IS_LOGGED_IN = "isLoggedIn"
        const val AUTH_TOKEN = "authToken"
        const val FULL_NAME= "fullName"
        const val EMAIL = "email"
        const val AGE = "age"
        const val GENDER = "gender"
    }
}
