package ru.startandroid.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.reactivex.Flowable
import io.reactivex.Single
import ru.startandroid.data.common.Constants
import ru.startandroid.data.database.model.WordEntity

@Dao
interface WordDao {

    @Query("SELECT * FROM ${Constants.DB_WORDS_TABLE_NAME} WHERE id = :wordId")
    fun getWord(wordId: Int): Single<WordEntity>

    @Query("SELECT * FROM ${Constants.DB_WORDS_TABLE_NAME}")
    fun getWords(): Flowable<List<WordEntity>>

    @Insert
    fun insertAll(words: List<WordEntity>)

    @Update
    fun updateAll(words: List<WordEntity>)

}