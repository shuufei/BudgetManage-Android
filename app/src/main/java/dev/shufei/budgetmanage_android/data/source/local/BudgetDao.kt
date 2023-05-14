package dev.shufei.budgetmanage_android.data.source.local

import androidx.room.*
import dev.shufei.budgetmanage_android.data.Budget
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget")
    fun observeBudgets(): Flow<List<Budget>>

    @Query("SELECT * FROM budget")
    suspend fun getAll(): List<Budget>

    @Query("SELECT * FROM budget WHERE id = :budgetId")
    fun observeBudget(budgetId: String): Flow<Budget>

    @Insert
    suspend fun insert(budget: Budget)

    @Update
    suspend fun update(budget: Budget)

    @Delete
    suspend fun delete(budget: Budget)
}