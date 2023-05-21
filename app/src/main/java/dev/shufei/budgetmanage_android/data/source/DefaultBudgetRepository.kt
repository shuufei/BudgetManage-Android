package dev.shufei.budgetmanage_android.data.source

import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.BudgetWithCategories
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

    override fun getBudgetStream(budgetId: String): Flow<Budget> {
        return budgetDataSource.getBudgetStream(budgetId)
    }

    override fun getBudgetWithCategoriesStream(budgetId: String): Flow<BudgetWithCategories> {
        return budgetDataSource.getBudgetWithCategoriesStream(budgetId)
    }

    override suspend fun getBudgets(): List<Budget> {
        return budgetDataSource.getBudgets()
    }

    override suspend fun addBudget(budget: Budget) {
        budgetDataSource.addBudget(budget = budget)
    }

    override suspend fun updateBudget(budget: Budget) {
        budgetDataSource.updateBudget(budget = budget)
    }

    override suspend fun deleteBudget(budget: Budget) {
        budgetDataSource.deleteBudget(budget = budget)
    }
}
