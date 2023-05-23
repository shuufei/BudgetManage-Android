package dev.shufei.budgetmanage_android.data.source

import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.CategoryWithBudget
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class DefaultCategoryRepository(
    private val categoryDataSource: CategoryDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryRepository {
    override fun getCategoriesStream(): Flow<List<Category>> {
        return categoryDataSource.getCategoriesStream()
    }

    override fun getCategoryWithBudgetStream(categoryId: String): Flow<CategoryWithBudget> {
        return categoryDataSource.getCategoryWithBudgetStream(categoryId)
    }

    override suspend fun getCategories(): List<Category> {
        return categoryDataSource.getCategories()
    }

    override suspend fun addCategory(category: Category) {
        return categoryDataSource.addCategory(category)
    }

    override suspend fun updateCategory(category: Category) {
        return categoryDataSource.updateCategory(category)
    }

    override suspend fun deleteCategory(category: Category) {
        return categoryDataSource.deleteCategory(category)
    }
}
