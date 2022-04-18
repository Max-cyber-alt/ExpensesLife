package com.example.expenseslife.data

import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences

@ExperimentalSplittiesApi
object SharedPrefs : Preferences("appPreferences") {
    var expensesFood by floatPref("expensesFood", defaultValue = 0.00F)
    var expensesFun by floatPref("expensesFun", defaultValue = 0.00F)
    var expensesThins by floatPref("expensesThings", defaultValue = 0.00F)
    var selectedExpensesType by stringPref("selectedExpensesType", defaultValue = "")

    fun resetFood() {
        expensesFood = 0.0F
    }

    fun resetFun() {
        expensesFun = 0.0F
    }

    fun resetThings() {
        expensesThins = 0.0F
    }
}