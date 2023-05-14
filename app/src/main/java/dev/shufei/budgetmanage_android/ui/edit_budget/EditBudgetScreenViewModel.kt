package dev.shufei.budgetmanage_android.ui.edit_budget

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.BudgetManageDestinationsArgs
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import javax.inject.Inject

@HiltViewModel
class EditBudgetScreenViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val budgetId: String = savedStateHandle[BudgetManageDestinationsArgs.BUDGET_ID]!!
    val budgetStream = budgetRepository.getBudgetStream(budgetId = budgetId)

    suspend fun update(budget: Budget) {
        budgetRepository.updateBudget(budget)
    }
}