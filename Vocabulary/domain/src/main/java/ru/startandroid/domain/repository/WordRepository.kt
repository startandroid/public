package ru.startandroid.domain.repository

import io.reactivex.Flowable
import io.reactivex.Observable
import ru.startandroid.domain.model.Word

interface WordRepository {

    fun getWords(): Flowable<List<Word>>

}