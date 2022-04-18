package com.example.expenseslife.data.model

import com.example.expenseslife.data.enums.ExpensesType

data class Expense(
    val expensesType: ExpensesType,
    val expenses: String
)
