package dev.shufei.budgetmanage_android.data.source.local

import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.CategoryWithBudget
import dev.shufei.budgetmanage_android.data.source.CategoryDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CategoryLocalDataSource internal constructor(
    private val categoryDao: CategoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CategoryDataSource {
    override fun getCategoriesStream(): Flow<List<Category>> {
        return categoryDao.observeCategories()
    }

    override fun getCategoryWithBudgetStream(categoryId: String): Flow<CategoryWithBudget> {
        return categoryDao.observeCategoryWithBudget(categoryId)
    }

    override suspend fun getCategories(): List<Category> {
        return categoryDao.getAll()
    }

    override suspend fun addCategory(category: Category) {
        withContext(ioDispatcher) {
            categoryDao.insert(category)
        }
    }

    override suspend fun updateCategory(category: Category) {
        withContext(ioDispatcher) {
            categoryDao.update(category)
        }
    }

    override suspend fun deleteCategory(category: Category) {
        withContext(ioDispatcher) {
            categoryDao.delete(category)
        }
    }
}