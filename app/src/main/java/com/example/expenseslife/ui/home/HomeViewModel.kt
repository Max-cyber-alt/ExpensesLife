package com.example.expenseslife.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenseslife.data.SharedPrefs
import com.example.expenseslife.data.enums.ExpensesType
import com.example.expenseslife.data.model.Expense
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
class HomeViewModel : ViewModel() {

    private val savedExpense = getSavedExpense()

    private val _title = MutableLiveData(savedExpense.expensesType.name)
    val title: LiveData<String> = _title

    private val _expenses = MutableLiveData(savedExpense.expenses)
    val expenses: LiveData<String> = _expenses

    private val _selectedExpType = MutableLiveData(savedExpense.expensesType)
    val selectedExpType = _selectedExpType

    private val _addExpenses = MutableLiveData<String>()
    val addExpenses: LiveData<String> = _addExpenses

    private val _totalExpenses = MutableLiveData(getTotalExpenses())
    val totalExpenses: LiveData<String> = _totalExpenses

    fun updateExpenses(newExpense: Float, expensesType: ExpensesType, success: () -> Unit) {
        val result = when (expensesType) {
            ExpensesType.FOOD -> {
                SharedPrefs.expensesFood.plus(newExpense).also {
                    SharedPrefs.expensesFood = it
                }
            }
            ExpensesType.FUN -> {
                SharedPrefs.expensesFun.plus(newExpense).also {
                    SharedPrefs.expensesFun = it
                }
            }
            ExpensesType.THINGS -> {
                SharedPrefs.expensesThins.plus(newExpense).also {
                    SharedPrefs.expensesThins = it
                }
            }
        }

        _expenses.value = String.format("%.2f", result)
        _totalExpenses.value = getTotalExpenses()
        _addExpenses.value = ""
        success.invoke()
    }

    fun changeExpensesType(expensesType: ExpensesType, success: () -> Unit) {
        val result: Float = when (expensesType) {
            ExpensesType.FOOD -> {
                SharedPrefs.expensesFood
            }
            ExpensesType.FUN -> {
                SharedPrefs.expensesFun
            }
            ExpensesType.THINGS -> {
                SharedPrefs.expensesThins
            }
        }

        SharedPrefs.selectedExpensesType = expensesType.name
        _title.value = expensesType.name
        _expenses.value = String.format("%.2f", result)
        _selectedExpType.value = expensesType
        success.invoke()
    }

    fun deleteExpenses(expensesType: ExpensesType) {
        when (expensesType) {
            ExpensesType.FOOD -> {
                SharedPrefs.resetFood()
            }
            ExpensesType.FUN -> {
                SharedPrefs.resetFun()
            }
            ExpensesType.THINGS -> {
                SharedPrefs.resetThings()
            }
        }
        _expenses.value = "0.00"
        _totalExpenses.value = getTotalExpenses()
    }

    private fun getTotalExpenses(): String {
        val total = SharedPrefs.expensesFood
            .plus(SharedPrefs.expensesFun)
            .plus(SharedPrefs.expensesThins)
        return String.format("%.2f", total)
    }

    private fun getSavedExpense(): Expense {
        val expensesType: ExpensesType
        val expenses: Float
        when (SharedPrefs.selectedExpensesType) {
            ExpensesType.FOOD.name -> {
                expensesType = ExpensesType.FOOD
                expenses = SharedPrefs.expensesFood
            }
            ExpensesType.FUN.name -> {
                expensesType = ExpensesType.FUN
                expenses = SharedPrefs.expensesFun
            }
            ExpensesType.THINGS.name -> {
                expensesType = ExpensesType.THINGS
                expenses = SharedPrefs.expensesThins
            }
            else -> {
                expensesType = ExpensesType.FOOD
                expenses = SharedPrefs.expensesFood
            }

        }

        return Expense(
            expensesType = expensesType,
            expenses = String.format("%.2f", expenses)
        )
    }
}