package com.kupriyanov.shoppinglistapp.presentation

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.kupriyanov.shoppinglistapp.R

interface OnTextChangedListener {
    fun onChanged()
}

@BindingAdapter("errorInputName")
fun bindErrorInputName(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_name)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("errorInputCount")
fun bindErrorInputCount(textInputLayout: TextInputLayout, isError: Boolean) {
    val message = if (isError) {
        textInputLayout.context.getString(R.string.error_input_count)
    } else {
        null
    }
    textInputLayout.error = message
}

@BindingAdapter("nameChangedListener")
fun bindNameChangedListener(editText: EditText, resetNameError: OnTextChangedListener) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            resetNameError.onChanged()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    })
}

@BindingAdapter("countChangedListener")
fun bindCountChangedListener(editText: EditText, resetCountError: OnTextChangedListener) {
    editText.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            resetCountError.onChanged()
        }

        override fun afterTextChanged(p0: Editable?) {
        }

    })
}