package dev.shufei.budgetmanage_android.data.source.local

import androidx.room.*
import dev.shufei.budgetmanage_android.data.Category
import dev.shufei.budgetmanage_android.data.CategoryWithBudget
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun observeCategories(): Flow<List<Category>>

    @Transaction
    @Query("SELECT * FROM category WHERE id = :categoryId")
    fun observeCategoryWithBudget(categoryId: String): Flow<CategoryWithBudget>

    @Query("SELECT * FROM category")
    suspend fun getAll(): List<Category>

    @Insert
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)
}
