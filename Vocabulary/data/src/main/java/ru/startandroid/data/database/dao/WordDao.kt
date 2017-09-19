package ru.startandroid.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ru.startandroid.data.common.Constants
import ru.startandroid.data.database.model.WordEntity

@Dao
interface WordDao {

    @Query("SELECT * FROM ${Constants.DB_WORDS_TABLE_NAME}")
    fun getWords(): Flowable<List<WordEntity>>

}