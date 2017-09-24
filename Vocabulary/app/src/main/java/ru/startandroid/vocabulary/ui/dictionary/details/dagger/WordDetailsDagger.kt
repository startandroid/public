package ru.startandroid.vocabulary.ui.dictionary.details.dagger

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import ru.startandroid.domain.usecase.word.GetWordById
import ru.startandroid.domain.usecase.word.InsertWords
import ru.startandroid.domain.usecase.word.UpdateWords
import ru.startandroid.vocabulary.base.dagger.BaseComponent
import ru.startandroid.vocabulary.base.dagger.BaseModule
import ru.startandroid.vocabulary.base.dagger.ComponentBuilder
import ru.startandroid.vocabulary.common.WordDetailsAction
import ru.startandroid.vocabulary.ui.dictionary.details.mvp.WordDetailsContract
import ru.startandroid.vocabulary.ui.dictionary.details.mvp.WordDetailsFragment
import ru.startandroid.vocabulary.ui.dictionary.details.mvp.WordDetailsCreatePresenter
import ru.startandroid.vocabulary.ui.dictionary.details.mvp.WordDetailsEditPresenter
import javax.inject.Scope

class WordDetailsDagger {

    @WordDetailsScope
    @Subcomponent(modules = arrayOf(WordDetailsModule::class))
    interface WordDetailsComponent : BaseComponent<WordDetailsFragment> {

        @Subcomponent.Builder
        interface Builder : ComponentBuilder<WordDetailsComponent, WordDetailsModule>

    }

    @Module
    data class WordDetailsModule(val action: WordDetailsAction): BaseModule {

        @Provides
        fun provideWordListContractPresenter(insertWords: InsertWords, updateWords: UpdateWords, getWordById: GetWordById) : WordDetailsContract.Presenter {
            Log.d("qweee", "create presenter action " + action)
            return when(action) {
                is WordDetailsAction.Create -> WordDetailsCreatePresenter(insertWords)
                is WordDetailsAction.Edit -> WordDetailsEditPresenter(updateWords, getWordById, action.id)
            }
        }

    }

    @Scope
    @Retention(AnnotationRetention.RUNTIME)
    annotation class WordDetailsScope

}