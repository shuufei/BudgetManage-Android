package dev.shufei.budgetmanage_android.data.source.local

import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.BudgetWithCategories
import dev.shufei.budgetmanage_android.data.source.BudgetDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class BudgetLocalDataSource internal constructor(
    private val budgetDao: BudgetDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BudgetDataSource {
    override fun getBudgetsStream(): Flow<List<Budget>> {
        return budgetDao.observeBudgets()
    }

    override fun getBudgetStream(budgetId: String): Flow<Budget> {
        return budgetDao.observeBudget(budgetId)
    }

    override fun getBudgetWithCategoriesStream(budgetId: String): Flow<BudgetWithCategories> {
        return budgetDao.observeBudgetWithCategories(budgetId)
    }

    override suspend fun getBudgets(): List<Budget> = withContext(ioDispatcher) {
        return@withContext try {
            budgetDao.getAll()
        } catch (e: java.lang.Exception) {
            emptyList()
        }
    }

    override suspend fun addBudget(budget: Budget) {
        withContext(ioDispatcher) {
            budgetDao.insert(budget)
        }
    }

    override suspend fun updateBudget(budget: Budget) {
        withContext(ioDispatcher) {
            budgetDao.update(budget)
        }
    }

    override suspend fun deleteBudget(budget: Budget) {
        withContext(ioDispatcher) {
            budgetDao.delete(budget)
        }
    }
}