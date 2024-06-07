package com.dicoding.wayfind.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.wayfind.R

class CustomName : AppCompatEditText, View.OnFocusChangeListener {

    private var isNameValid = false

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
        inputType = InputType.TYPE_CLASS_TEXT
        onFocusChangeListener = this
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateName()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (!hasFocus) {
            validateName()
        }
    }

    private fun validateName() {
        val name = text.toString().trim()
        isNameValid = name.isNotEmpty() && name.length <= 25
        error = if (!isNameValid) {
            if (name.isEmpty()) {
                resources.getString(R.string.ERROR_NAME_EMPTY)
            } else {
                resources.getString(R.string.ERROR_NAME_TOOLONG)
            }
        } else {
            null
        }
    }
}