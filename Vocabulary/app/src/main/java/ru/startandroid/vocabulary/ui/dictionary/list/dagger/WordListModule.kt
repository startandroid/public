package ru.startandroid.vocabulary.ui.dictionary.list.dagger

import dagger.Module
import dagger.Provides
import ru.startandroid.vocabulary.base.dagger.BaseModule
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListContract
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListPresenter

@Module
class WordListModule: BaseModule {

    @Provides
    fun provideWordListContractPresenter() : WordListContract.Presenter {
        return WordListPresenter();
    }

}