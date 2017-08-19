package ru.startandroid.vocabulary.app.dagger

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import ru.startandroid.data.database.DatabaseScheduler
import ru.startandroid.data.database.dao.AppDatabase
import ru.startandroid.data.database.dao.WordDao
import ru.startandroid.data.database.mapper.WordMapper
import ru.startandroid.data.repository.WordRepositoryImpl
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Module
class DatabaseModule {

    @AppScope
    @Provides
    fun provideAppDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "vocabulary").build()

    @AppScope
    @Provides
    fun provideWordDao(appDatabase: AppDatabase) = appDatabase.wordDao()

    @Provides
    fun provideWordMapper() = WordMapper()

    @AppScope
    @Provides
    fun provideWordRepository(wordDao: WordDao, wordMapper: WordMapper, @DatabaseScheduler scheduler: Scheduler) = WordRepositoryImpl(wordDao, wordMapper, scheduler)

    @AppScope
    @DatabaseScheduler
    @Provides
    fun provideDatabaseSheduler(@DatabaseScheduler executor: Executor) = Schedulers.from(executor)


    @AppScope
    @DatabaseScheduler
    @Provides
    fun provideDatabaseExecutor() = Executors.newSingleThreadExecutor()
}