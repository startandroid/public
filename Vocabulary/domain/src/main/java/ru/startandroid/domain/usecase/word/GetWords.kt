package ru.startandroid.domain.usecase.word

import io.reactivex.Flowable
import io.reactivex.Observable
import ru.startandroid.domain.model.Word
import ru.startandroid.domain.repository.WordRepository
import ru.startandroid.domain.usecase.base.FlowableUseCase
import ru.startandroid.domain.usecase.base.ObservableUseCase

class GetWords(val wordRepository: WordRepository) : FlowableUseCase<List<Word>>() {
    override fun createObservable(): Flowable<List<Word>> = wordRepository.getWords()
}