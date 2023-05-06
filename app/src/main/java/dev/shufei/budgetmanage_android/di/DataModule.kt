package dev.shufei.budgetmanage_android.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.shufei.budgetmanage_android.data.source.local.AppUiStateLocalDataSource
import dev.shufei.budgetmanage_android.data.source.local.dataStore
import dev.shufei.budgetmanage_android.data.source.*
import dev.shufei.budgetmanage_android.data.source.local.AppDatabase
import dev.shufei.budgetmanage_android.data.source.local.BudgetLocalDataSource
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalBudgetDataSource

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalAppUiStateDataSource

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

    @Singleton
    @Provides
    fun provideAppUiStateRepository(
        @LocalAppUiStateDataSource localDataSource: AppUiStateDataSource
    ): AppUiStateRepository {
        return DefaultAppUiStateRepository(localDataSource)
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

    @Singleton
    @LocalAppUiStateDataSource
    @Provides
    fun provideAppUiStateLocalDataSource(
        dataStore: DataStore<Preferences>
    ): AppUiStateDataSource {
        return AppUiStateLocalDataSource(dataStore)
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

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
}
