package dev.shufei.budgetmanage_android.data.source

import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.BudgetWithCategories
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {
    fun getBudgetsStream(): Flow<List<Budget>>
    fun getBudgetStream(budgetId: String): Flow<Budget>
    suspend fun getBudgetWithCategoriesStream(budgetId: String): Flow<BudgetWithCategories>
    suspend fun getBudgets(): List<Budget>
    suspend fun addBudget(budget: Budget)
    suspend fun updateBudget(budget: Budget)
    suspend fun deleteBudget(budget: Budget)
}
