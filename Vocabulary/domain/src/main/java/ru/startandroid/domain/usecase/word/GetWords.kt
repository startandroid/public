package ru.startandroid.domain.usecase.word

import io.reactivex.Observable
import ru.startandroid.domain.model.Word
import ru.startandroid.domain.repository.WordRepository
import ru.startandroid.domain.usecase.base.ObservableUseCase

class GetWords(val wordRepository: WordRepository) : ObservableUseCase<List<Word>>() {
    override fun createObservable(): Observable<List<Word>> = wordRepository.getWords()
}