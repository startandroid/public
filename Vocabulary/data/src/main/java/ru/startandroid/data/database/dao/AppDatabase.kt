package ru.startandroid.data.database.dao

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ru.startandroid.data.database.model.WordEntity

@Database(entities = arrayOf(WordEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}