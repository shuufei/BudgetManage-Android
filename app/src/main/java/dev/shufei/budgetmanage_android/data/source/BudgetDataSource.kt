package dev.shufei.budgetmanage_android.data.source

import dev.shufei.budgetmanage_android.data.Budget
import kotlinx.coroutines.flow.Flow

interface BudgetDataSource {
    fun getBudgetsStream(): Flow<List<Budget>>
    fun getBudgetStream(budgetId: String): Flow<Budget>
    suspend fun getBudgets(): List<Budget>
    suspend fun addBudget(budget: Budget)
    suspend fun deleteBudget(budget: Budget)
}
