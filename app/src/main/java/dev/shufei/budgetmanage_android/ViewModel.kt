package dev.shufei.budgetmanage_android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.shufei.budgetmanage_android.data.AppDatabase
import dev.shufei.budgetmanage_android.data.Budget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel constructor(val db: AppDatabase) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadBudgets()
    }

    fun addBudget(
        budget: Budget
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            db.budgetDao().insert(budget)
            loadBudgets()
        }
    }

    private fun loadBudgets() {
        viewModelScope.launch(Dispatchers.IO) {
            val budgets = db.budgetDao().getAll()
            _uiState.update { it.copy(budgets = budgets) }
        }
    }

    data class UiState(
        val budgets: List<Budget> = emptyList()
    )
}