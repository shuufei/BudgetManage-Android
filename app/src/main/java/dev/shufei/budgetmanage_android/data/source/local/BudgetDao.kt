package dev.shufei.budgetmanage_android.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.shufei.budgetmanage_android.data.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget")
    fun observeBudgets(): Flow<List<Budget>>

    @Query("SELECT * FROM budget")
    suspend fun getAll(): List<Budget>

    @Insert
    suspend fun insert(budget: Budget)

    @Delete
    suspend fun delete(budget: Budget)
}