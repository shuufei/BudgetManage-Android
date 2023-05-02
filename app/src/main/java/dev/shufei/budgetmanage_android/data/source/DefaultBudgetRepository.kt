package dev.shufei.budgetmanage_android.data.source

import dev.shufei.budgetmanage_android.data.Budget
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class DefaultBudgetRepository(
    private val budgetDataSource: BudgetDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BudgetRepository {
    override fun getBudgetsStream(): Flow<List<Budget>> {
        return budgetDataSource.getBudgetsStream()
    }

    override suspend fun getBudgets(): List<Budget> {
        return budgetDataSource.getBudgets()
    }

    override suspend fun addBudget(budget: Budget) {
        budgetDataSource.addBudget(budget = budget)
    }
}
