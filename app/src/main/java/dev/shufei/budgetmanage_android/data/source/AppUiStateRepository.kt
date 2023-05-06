package dev.shufei.budgetmanage_android.data.source

import kotlinx.coroutines.flow.Flow

interface AppUiStateRepository {
    fun getActiveBudgetIdStream(): Flow<String?>
    suspend fun setActiveBudgetId(budgetId: String): Unit
}
