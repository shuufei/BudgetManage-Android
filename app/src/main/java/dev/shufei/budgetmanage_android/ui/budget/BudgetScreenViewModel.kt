package dev.shufei.budgetmanage_android.ui.budget

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.source.AppUiStateRepository
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import javax.inject.Inject

@HiltViewModel
class BudgetScreenViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val appUiStateRepository: AppUiStateRepository
) : ViewModel() {
    val budgetsStream = budgetRepository.getBudgetsStream()
    val activeBudgetIdStream = appUiStateRepository.getActiveBudgetIdStream()

    suspend fun delete(budget: Budget) {
        budgetRepository.deleteBudget(budget)
    }

    suspend fun setActiveBudgetId(budgetId: String) {
        appUiStateRepository.setActiveBudgetId(budgetId = budgetId)
    }
}

data class UiState(
    val budgets: List<Budget> = emptyList()
)
