package dev.shufei.budgetmanage_android.ui.budget

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import javax.inject.Inject

@HiltViewModel
class BudgetScreenViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
) : ViewModel() {
    val budgetsStream = budgetRepository.getBudgetsStream()

    suspend fun delete(budget: Budget) {
        budgetRepository.deleteBudget(budget)
    }
}

data class UiState(
    val budgets: List<Budget> = emptyList()
)
