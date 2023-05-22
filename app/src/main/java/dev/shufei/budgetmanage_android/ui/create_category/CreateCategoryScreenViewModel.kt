package dev.shufei.budgetmanage_android.ui.create_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.BudgetManageDestinationsArgs
import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.source.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class CreateCategoryScreenViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val budgetId: String = savedStateHandle[BudgetManageDestinationsArgs.BUDGET_ID]!!

    suspend fun addCategory(
        category: Category
    ) {
        categoryRepository.addCategory(category)
    }
}