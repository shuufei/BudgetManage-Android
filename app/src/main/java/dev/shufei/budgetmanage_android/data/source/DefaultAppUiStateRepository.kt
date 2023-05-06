package dev.shufei.budgetmanage_android.data.source

import kotlinx.coroutines.flow.Flow

class DefaultAppUiStateRepository constructor(
    private val dataSource: AppUiStateDataSource
) : AppUiStateRepository {
    override fun getActiveBudgetIdStream(): Flow<String?> {
        return dataSource.getActiveBudgetIdStream()
    }

    override suspend fun setActiveBudgetId(budgetId: String) {
        dataSource.setActiveBudgetId(budgetId = budgetId)
    }
}