package ru.startandroid.vocabulary.app.dagger

import dagger.Module
import dagger.Provides
import ru.startandroid.domain.repository.WordRepository
import ru.startandroid.domain.usecase.word.GetWords

@Module
class UseCaseModule {

    @Provides
    fun provideGetWords(wordRepository: WordRepository) = GetWords(wordRepository)


}