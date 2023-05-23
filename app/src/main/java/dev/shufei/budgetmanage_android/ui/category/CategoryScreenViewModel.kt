package dev.shufei.budgetmanage_android.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.BudgetManageDestinationsArgs
import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import dev.shufei.budgetmanage_android.data.source.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val categoryId: String = savedStateHandle[BudgetManageDestinationsArgs.CATEGORY_ID]!!

    val category = categoryRepository.getCategoryWithBudgetStream(categoryId)

    suspend fun delete(category: Category) {
        categoryRepository.deleteCategory(category)
    }
}
