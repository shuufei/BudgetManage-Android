package dev.shufei.budgetmanage_android.ui.edit_category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.shufei.budgetmanage_android.BudgetManageDestinationsArgs
import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.source.CategoryRepository
import javax.inject.Inject

@HiltViewModel
class EditCategoryScreenViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val categoryId: String = savedStateHandle[BudgetManageDestinationsArgs.CATEGORY_ID]!!
    val categoryWithBudgetStream = categoryRepository.getCategoryWithBudgetStream(categoryId)

    suspend fun update(category: Category) {
        categoryRepository.updateCategory(category)
    }
}