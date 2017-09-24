package ru.startandroid.domain.usecase.word

import io.reactivex.Single
import ru.startandroid.domain.model.Word
import ru.startandroid.domain.repository.WordRepository
import ru.startandroid.domain.usecase.base.SingleUseCaseWithParameter

class GetWordById(val wordRepository: WordRepository): SingleUseCaseWithParameter<Int, Word>() {
    override fun createObservable(wordId: Int): Single<Word> = wordRepository.getWord(wordId)
}