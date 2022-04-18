package com.example.expenseslife.ui.home

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.PopupMenu
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.expenseslife.R
import com.example.expenseslife.data.enums.ExpensesType
import com.example.expenseslife.databinding.ActivityHomeBinding
import com.example.expenseslife.util.extensions.*
import splitties.experimental.ExperimentalSplittiesApi
import java.util.*

@ExperimentalSplittiesApi
class HomeActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }
    private lateinit var selectedExpType: ExpensesType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        setupObservers()
        setupListeners()
    }

    override fun onStart() {
        super.onStart()

        loadStartAnimation()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_food -> {
                handleMenuClick(ExpensesType.FOOD)
                true
            }
            R.id.action_fun -> {
                handleMenuClick(ExpensesType.FUN)
                true
            }
            R.id.action_things -> {
                handleMenuClick(ExpensesType.THINGS)
                true
            }
            else -> false
        }
    }

    fun showExpensesMenu(v: View) {
        PopupMenu(this, v).apply {
            setOnMenuItemClickListener(this@HomeActivity)
            inflate(R.menu.expenses_menu)
            show()
        }
    }

    fun showDeleteDialog(@Suppress("UNUSED_PARAMETER") view: View?) {
        if (homeViewModel.expenses.value == "0.00") return

        displayAlertDialog(
            title = getString(R.string.delete_all_expenses, selectedExpType.name),
            positiveButtonAction = {
                homeViewModel.deleteExpenses(selectedExpType)
                showToast(getString(R.string.expenses_deleted, selectedExpType.name))
            },
            negativeButtonText = R.string.cancel
        )
    }

    private fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel

        binding.moneyEditText.setupDecimalFormat()
    }

    private fun setupObservers() {
        homeViewModel.selectedExpType.observe(this, {
            selectedExpType = it
        })
    }

    private fun setupListeners() {
        binding.moneyEditText.setOnEditorActionListener(OnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                updateExpenses(v.text.toString().toFloat())
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun handleMenuClick(expensesType: ExpensesType) {
        if (selectedExpType == expensesType) return

        homeViewModel.changeExpensesType(expensesType) {
            loadUpdatedTypeAnimation()
            showToast(R.string.type_changed)
        }
    }

    private fun updateExpenses(newExpense: Float) {
        homeViewModel.updateExpenses(newExpense, selectedExpType) {
            loadUpdatedExpensesAnimation()
            showToast(R.string.expenses_updated)
        }
    }

    private fun loadStartAnimation() {
        binding.apply {
            updateImageView.startRightAppearanceAnimation(applicationContext)
            deleteImageView.startRightAppearanceAnimation(applicationContext)
            expensesTypeTextView.startZoomAnimation500(applicationContext)
            moneyEditText.startZoomAnimation500(applicationContext)
            expensesTetView.startLeftAppearanceAnimation(applicationContext)
            totalTextView.startLeftAppearanceAnimation(applicationContext)
        }
    }

    private fun loadUpdatedTypeAnimation() {
        binding.expensesTetView.startRotateAnimation(applicationContext)
        binding.expensesTypeTextView.startRotateAnimation(applicationContext)
    }

    private fun loadUpdatedExpensesAnimation() {
        binding.expensesTetView.startZoomAnimation300(applicationContext)
        binding.totalTextView.startZoomAnimation300(applicationContext)
    }

}