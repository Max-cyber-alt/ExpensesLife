package com.example.expenseslife.data

import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.Preferences
import splitties.preferences.SuspendPrefsAccessor

@ExperimentalSplittiesApi
object SharedPrefs : Preferences("appPreferences") {
    var expensesFood by stringPref("expensesFood", defaultValue = "")
    var expensesFun by stringPref("expensesFun", defaultValue = "")
    var expensesThins by stringPref("expensesThings", defaultValue = "")
    var selectedExpensesType by stringPref("selectedExpensesType", defaultValue = "")

    fun resetFood() {
        expensesFood = ""
    }

    fun resetFun() {
        expensesFun = ""
    }

    fun resetThings() {
        expensesThins = ""
    }
}