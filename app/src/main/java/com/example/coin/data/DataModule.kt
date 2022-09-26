package com.example.coin.data

import android.content.Context
import androidx.room.Room
import com.example.coin.domain.RecordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Класс для реализации DI в модуле данных
 *
 */
@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    fun provideRecordDao(appDatabase: Database): RecordDao {
        return appDatabase.recordDatabase()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): Database {
        return Room.databaseBuilder(
            appContext,
            Database::class.java,
            "RssReader"
        ).build()
    }

    @Provides
    fun provideRepoImpl(recordRepositoryImpl: RecordRepositoryImpl): RecordRepository {
        return recordRepositoryImpl
    }
}