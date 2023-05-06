package dev.shufei.budgetmanage_android.data.source.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.shufei.budgetmanage_android.data.source.AppUiStateDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app-ui-state")

class AppUiStateLocalDataSource internal constructor(
    private val dataStore: DataStore<Preferences>
): AppUiStateDataSource {
    private val activeBudgetIdStream: Flow<String?> = this.dataStore.data
        .map { it[PreferenceKeys.ACTIVE_BUDGET_ID] ?: null }

    override fun getActiveBudgetIdStream(): Flow<String?> {
        return activeBudgetIdStream
    }

    override suspend fun setActiveBudgetId(
        budgetId: String
    ) {
        this.dataStore.edit {
            it[PreferenceKeys.ACTIVE_BUDGET_ID] = budgetId
        }
    }
}

private object PreferenceKeys {
    val ACTIVE_BUDGET_ID = stringPreferencesKey("activeBudgetId")
}
