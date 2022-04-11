package com.example.expenseslife.util.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.setupDecimalFormat() {

    this.addTextChangedListener(object : TextWatcher {

        private val maxLengthAfterDotRegEx = Regex("^[0-9]*\\.[0-9]{2}$")
        private val dotAtTheEndRegEx = Regex("^[0-9]{6}\\.\$")
        private var oldText = ""
        private var oldLength = 0

        override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            oldText = s.toString()
            oldLength = s?.length ?: 0
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(s: Editable?) {
            val isMaxLengthAfterDot = oldText.matches(maxLengthAfterDotRegEx)
            val isDotAtTheEnd = s.toString().matches(dotAtTheEndRegEx)
            val currentLength = s?.length ?: 0

            if (oldLength < currentLength) {
                if (isMaxLengthAfterDot || isDotAtTheEnd) {
                    this.apply {
                        setText(oldText)
                        setSelection(length())
                    }
                }
            }
        }
    })
}