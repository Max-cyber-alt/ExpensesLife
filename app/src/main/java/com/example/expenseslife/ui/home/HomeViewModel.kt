package com.example.expenseslife.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expenseslife.data.model.Expense
import com.example.expenseslife.data.SharedPrefs
import com.example.expenseslife.data.enums.ExpensesType
import com.example.expenseslife.util.extensions.convertToDouble
import com.example.expenseslife.util.extensions.isMinus
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
class HomeViewModel : ViewModel() {

    private val savedExpense = getSavedExpense()

    private val _title = MutableLiveData(savedExpense.expensesType.name)
    val title: LiveData<String> = _title

    private val _expenses = MutableLiveData(savedExpense.expenses)
    val expenses: LiveData<Double> = _expenses

    private val _selectedExpType = MutableLiveData(savedExpense.expensesType)
    val selectedExpType = _selectedExpType

    private val _addExpenses = MutableLiveData<String>()
    val addExpenses: LiveData<String> = _addExpenses

    private val _totalExpenses = MutableLiveData(getTotalExpenses())
    val totalExpenses: LiveData<Double> = _totalExpenses

    fun updateExpenses(expense: String, expensesType: ExpensesType, success: () -> Unit) {
        val result = when (expensesType) {
            ExpensesType.FOOD -> {
                getUpdatedExpenses(expense, SharedPrefs.expensesFood).also {
                    SharedPrefs.expensesFood = it.toString()
                }
            }
            ExpensesType.FUN -> {
                getUpdatedExpenses(expense, SharedPrefs.expensesFun).also {
                    SharedPrefs.expensesFun = it.toString()
                }
            }
            ExpensesType.THINGS -> {
                getUpdatedExpenses(expense, SharedPrefs.expensesThins).also {
                    SharedPrefs.expensesThins = it.toString()
                }
            }
        }

        _expenses.value = result
        _totalExpenses.value = getTotalExpenses()
        _addExpenses.value = ""
        success.invoke()
    }

    fun changeExpensesType(expensesType: ExpensesType, success: () -> Unit) {
        when (expensesType) {
            ExpensesType.FOOD -> {
                _expenses.value = SharedPrefs.expensesFood.convertToDouble()
            }
            ExpensesType.FUN -> {
                _expenses.value = SharedPrefs.expensesFun.convertToDouble()
            }
            ExpensesType.THINGS -> {
                _expenses.value = SharedPrefs.expensesThins.convertToDouble()
            }
        }

        SharedPrefs.selectedExpensesType = expensesType.name
        _title.value = expensesType.name
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
        _expenses.value = 0.0
        _totalExpenses.value = getTotalExpenses()
    }

    private fun getUpdatedExpenses(expense: String, expenses: String): Double {
        return if (expense.isMinus()) {
            expenses.convertToDouble().minus(expense.convertToDouble())
        } else {
            expenses.convertToDouble().plus(expense.convertToDouble())
        }
    }

    private fun getTotalExpenses(): Double {
        return SharedPrefs.expensesFood.convertToDouble()
            .plus(SharedPrefs.expensesFun.convertToDouble())
            .plus(SharedPrefs.expensesThins.convertToDouble())
    }

    private fun getSavedExpense(): Expense {
        return when (SharedPrefs.selectedExpensesType) {
            ExpensesType.FOOD.name -> {
                Expense(
                    expensesType = ExpensesType.FOOD,
                    expenses = SharedPrefs.expensesFood.convertToDouble()
                )
            }
            ExpensesType.FUN.name -> {
                Expense(
                    expensesType = ExpensesType.FUN,
                    expenses = SharedPrefs.expensesFun.convertToDouble()
                )
            }
            ExpensesType.THINGS.name -> {
                Expense(
                    expensesType = ExpensesType.THINGS,
                    expenses = SharedPrefs.expensesThins.convertToDouble()
                )
            }
            else -> Expense(
                expensesType = ExpensesType.FOOD,
                expenses = SharedPrefs.expensesFood.convertToDouble()
            )
        }
    }
}