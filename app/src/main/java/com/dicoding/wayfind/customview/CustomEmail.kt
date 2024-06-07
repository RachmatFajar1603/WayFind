package com.dicoding.wayfind.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.wayfind.R

class CustomEmail : AppCompatEditText, View.OnFocusChangeListener {

    var isEmailValid = false
    private var isEmailTaken = false
    private lateinit var registeredEmail: String

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
//        setBackgroundResource(R.drawable.edit_text_background)
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        onFocusChangeListener = this
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateEmail()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            validateEmail()
        }
    }

    private fun validateEmail() {
        val email = text.toString().trim()
        isEmailValid = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        error = if (!isEmailValid) {
            if (email.isEmpty()) {
                resources.getString(R.string.ERROR_EMAIL_EMPTY)
            } else {
                resources.getString(R.string.ERROR_EMAIL_INVALID_FORMAT)
            }
        } else if (isEmailTaken && email == registeredEmail) {
            resources.getString(R.string.ERROR_EMAIL_ALREADY_REGISTERED)
        } else {
            null
        }
    }
}
