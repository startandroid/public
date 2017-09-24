package ru.startandroid.data.database.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import ru.startandroid.data.common.Constants

@Entity(tableName = Constants.DB_WORDS_TABLE_NAME)
data class WordEntity (
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val value: String,
        val translate: String,
        val sample: String,
        val definition: String,
        val rememberedCount: Int,
        val added: Long
)
