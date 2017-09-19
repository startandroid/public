package ru.startandroid.vocabulary.ui.dictionary.list.dagger

import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.startandroid.domain.usecase.word.GetWords
import ru.startandroid.vocabulary.base.dagger.BaseComponent
import ru.startandroid.vocabulary.base.dagger.BaseModule
import ru.startandroid.vocabulary.base.dagger.ComponentBuilder
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListContract
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListFragment
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListPresenter
import javax.inject.Scope

class WordListDagger {

    @WordListScope
    @Subcomponent(modules = arrayOf(WordListModule::class))
    interface WordListComponent : BaseComponent<WordListFragment> {

        @Subcomponent.Builder
        interface Builder : ComponentBuilder<WordListComponent, WordListModule>

    }

    @Module
    class WordListModule: BaseModule {

        @Provides
        fun provideWordListContractPresenter(getWords: GetWords) : WordListContract.Presenter {
            return WordListPresenter(getWords);
        }

    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class WordListScope

}