package dev.shufei.budgetmanage_android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadBudgets()
    }

    fun addBudget(
        budget: Budget
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            budgetRepository.addBudget(budget)
            loadBudgets()
        }
    }

    private fun loadBudgets() {
        viewModelScope.launch(Dispatchers.IO) {
            val budgets = budgetRepository.getBudgets()
            _uiState.update { it.copy(budgets = budgets) }
        }
    }

    data class UiState(
        val budgets: List<Budget> = emptyList()
    )
}