package com.example.expenseslife.util.extensions

import android.content.res.Resources
import android.util.DisplayMetrics

fun Int.dpToPx(): Int {
    val metrics = Resources.getSystem().displayMetrics
    return Math.round(this * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
}