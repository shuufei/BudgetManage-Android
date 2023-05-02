package dev.shufei.budgetmanage_android.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.shufei.budgetmanage_android.data.Budget

@Database(entities = [Budget::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}
