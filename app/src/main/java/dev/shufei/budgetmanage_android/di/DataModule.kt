package dev.shufei.budgetmanage_android.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.shufei.budgetmanage_android.data.source.BudgetDataSource
import dev.shufei.budgetmanage_android.data.source.BudgetRepository
import dev.shufei.budgetmanage_android.data.source.DefaultBudgetRepository
import dev.shufei.budgetmanage_android.data.source.local.AppDatabase
import dev.shufei.budgetmanage_android.data.source.local.BudgetLocalDataSource
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalBudgetDataSource

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideBudgetRepository(
        @LocalBudgetDataSource localDataSource: BudgetDataSource
    ): BudgetRepository {
        return DefaultBudgetRepository(localDataSource)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @LocalBudgetDataSource
    @Provides
    fun provideBudgetLocalDataSource(
        database: AppDatabase
    ): BudgetDataSource {
        return BudgetLocalDataSource(database.budgetDao())
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "budget-manager-db"
        ).build()
    }
}
