package dev.shufei.budgetmanage_android.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.shufei.budgetmanage_android.data.Budget
import dev.shufei.budgetmanage_android.data.Category

@Database(entities = [Budget::class, Category::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
    abstract fun categoryDao(): CategoryDao
}
