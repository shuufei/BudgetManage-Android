package dev.shufei.budgetmanage_android.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BudgetDao {
    @Query("SELECT * FROM budget")
    fun getAll(): List<Budget>

    @Insert
    suspend fun insert(budget: Budget)

    @Delete
    fun delete(budget: Budget)
}