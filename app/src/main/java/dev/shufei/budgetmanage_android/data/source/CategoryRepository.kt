package dev.shufei.budgetmanage_android.data.source

import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.CategoryWithBudget
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategoriesStream(): Flow<List<Category>>
    fun getCategoryWithBudgetStream(categoryId: String): Flow<CategoryWithBudget>
    suspend fun getCategories(): List<Category>
    suspend fun addCategory(category: Category)
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(category: Category)
}