package ru.startandroid.vocabulary.ui.dictionary.list.mvp

import ru.startandroid.domain.usecase.word.GetWords
import ru.startandroid.vocabulary.base.mvp.BasePresenter

class WordListPresenter (val getWords: GetWords) : BasePresenter<WordListContract.View>(), WordListContract.Presenter {

    override fun onViewIsReady(firstTime: Boolean) {


    }


}