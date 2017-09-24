package ru.startandroid.domain.usecase.word

import io.reactivex.Completable
import ru.startandroid.domain.model.Word
import ru.startandroid.domain.repository.WordRepository
import ru.startandroid.domain.usecase.base.CompletableUseCaseWithParameter

class UpdateWords(val wordRepository: WordRepository): CompletableUseCaseWithParameter<List<Word>>() {

    override fun createObservable(words: List<Word>): Completable = wordRepository.updateWords(words)

}