package dev.shufei.budgetmanage_android.ui.create_budget

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import javax.inject.Inject

@HiltViewModel
class CreateBudgetScreenViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository
) : ViewModel() {
    suspend fun addBudget(
        budget: Budget
    ) {
        budgetRepository.addBudget(budget)
    }
}