package ru.startandroid.vocabulary.ui.dictionary.details.mvp

import ru.startandroid.domain.model.Word
import ru.startandroid.vocabulary.base.mvp.MvpPresenter
import ru.startandroid.vocabulary.base.mvp.MvpView

class WordDetailsContract {

    interface View : MvpView {
        fun fillWord(word: Word?)
        fun showWord(word: Word?)
        fun closeScreen()
    }

    interface Presenter : MvpPresenter<View> {
        fun onSaveClick()
    }



}