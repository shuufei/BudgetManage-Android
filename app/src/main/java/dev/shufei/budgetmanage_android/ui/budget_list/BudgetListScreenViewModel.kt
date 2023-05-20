package dev.shufei.budgetmanage_android.ui.budget_list

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import dev.shufei.budgetmanage_android.data.source.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class BudgetListScreenViewModel @Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {
    val budgetsStream = budgetRepository.getBudgetsStream()
    val categoriesStream = categoryRepository.getCategoriesStream()

    suspend fun delete(budget: Budget) {
        budgetRepository.deleteBudget(budget)
    }
}
