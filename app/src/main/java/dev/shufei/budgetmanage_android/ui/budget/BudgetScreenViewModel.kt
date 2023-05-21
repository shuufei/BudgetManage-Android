package dev.shufei.budgetmanage_android.ui.budget

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.BudgetManageDestinationsArgs
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.source.AppUiStateRepository
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class BudgetScreenViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val budgetId: String = savedStateHandle[BudgetManageDestinationsArgs.BUDGET_ID]!!
    val budgetStream = budgetRepository.getBudgetStream(budgetId = budgetId)
    val budgetWithCategoriesStream = budgetRepository.getBudgetWithCategoriesStream(budgetId = budgetId)

    suspend fun delete(budget: Budget) {
        budgetRepository.deleteBudget(budget)
    }
}

