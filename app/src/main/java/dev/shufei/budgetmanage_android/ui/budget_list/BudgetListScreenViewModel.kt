package dev.shufei.budgetmanage_android.ui.budget_list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import javax.inject.Inject

@HiltViewModel
class BudgetListScreenViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
) : ViewModel() {
    val budgetsStream = budgetRepository.getBudgetsStream()

    suspend fun delete(budget: Budget) {
        budgetRepository.deleteBudget(budget)
    }
}
