package ru.startandroid.domain.repository

import io.reactivex.Observable
import ru.startandroid.domain.model.Word

interface WordRepository {

    fun getWords(): Observable<List<Word>>

}