package dev.shufei.budgetmanage_android.data.source.local

import dev.shufei.budgetmanage_android.data.Budget
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
}