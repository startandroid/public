package ru.startandroid.vocabulary.app.dagger

import dagger.Module
import dagger.Provides
import ru.startandroid.domain.repository.WordRepository
import ru.startandroid.domain.usecase.word.GetWordById
import ru.startandroid.domain.usecase.word.GetWords
import ru.startandroid.domain.usecase.word.InsertWords
import ru.startandroid.domain.usecase.word.UpdateWords

@Module
class UseCaseModule {

    @Provides
    fun provideGetWordById(wordRepository: WordRepository) = GetWordById(wordRepository)

    @Provides
    fun provideGetWords(wordRepository: WordRepository) = GetWords(wordRepository)

    @Provides
    fun provideInsertWords(wordRepository: WordRepository) = InsertWords(wordRepository)

    @Provides
    fun provideUpdateWords(wordRepository: WordRepository) = UpdateWords(wordRepository)

}