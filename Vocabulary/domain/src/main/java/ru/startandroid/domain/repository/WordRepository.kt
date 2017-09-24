package ru.startandroid.domain.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import ru.startandroid.domain.model.Word

interface WordRepository {

    fun getWord(wordId: Int): Single<Word>
    fun getWords(): Flowable<List<Word>>
    fun insertWords(words: List<Word>): Completable
    fun updateWords(words: List<Word>): Completable

}