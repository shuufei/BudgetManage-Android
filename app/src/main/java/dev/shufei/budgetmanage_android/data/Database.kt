package dev.shufei.budgetmanage_android.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Budget::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun budgetDao(): BudgetDao
}
