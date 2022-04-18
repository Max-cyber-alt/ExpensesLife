package com.example.expenseslife.util.extensions

import android.content.DialogInterface
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.expenseslife.R

fun AppCompatActivity.displayAlertDialog(
    title: String? = null,
    message: String? = null,
    @StringRes positiveButtonText: Int = R.string.common_ok,
    @StringRes neutralButtonText: Int? = null,
    @StringRes negativeButtonText: Int? = null,
    @StringRes textFieldHint: Int? = null,
    isCancellable: Boolean = false,
    isButtonAllCaps: Boolean = false,
    neutralButtonAction: (() -> Unit)? = null,
    negativeButtonAction: (() -> Unit)? = null,
    positiveButtonAction: ((String) -> Unit)? = null
): AlertDialog? {
    if (isFinishing) return null
    val builder = AlertDialog.Builder(this)
    val alertDialog = builder.create()

    builder.apply {
        setCancelable(isCancellable)
        val editText = EditText(context)
        if (title != null) setTitle(title)
        if (message != null) setMessage(message)

        if (textFieldHint != null) {
            val container = LinearLayout(context)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            lp.setMargins(16.dpToPx(), 0, 16.dpToPx(), 0)
            editText.layoutParams = lp
            editText.hint = getString(textFieldHint)
            container.addView(editText)
            builder.setView(container)
        }

        if (negativeButtonText != null) setNegativeButton(negativeButtonText) { _, _ ->
            alertDialog.dismiss()
            negativeButtonAction?.invoke()
        }
        if (neutralButtonText != null) setNeutralButton(neutralButtonText) { _, _ ->
            alertDialog.dismiss()
            neutralButtonAction?.invoke()
        }
        setPositiveButton(positiveButtonText) { _, _ ->
            alertDialog.dismiss()
            positiveButtonAction?.invoke(editText.text.toString())
        }
    }

    return builder.show().apply {
        getButton(DialogInterface.BUTTON_POSITIVE)?.isAllCaps = isButtonAllCaps
        getButton(DialogInterface.BUTTON_NEGATIVE)?.isAllCaps = isButtonAllCaps
        getButton(DialogInterface.BUTTON_NEUTRAL)?.isAllCaps = isButtonAllCaps
    }
}

fun AppCompatActivity.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

