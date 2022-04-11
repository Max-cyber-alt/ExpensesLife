package com.example.expenseslife.util.extensions


fun String.convertToDouble(): Double {
    return try {
        this.toDouble()
    } catch (e: NumberFormatException) {
        0.0
    }
}

fun String.isMinus(): Boolean {
    return this.matches(Regex("^[-]"))
}