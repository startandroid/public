package ru.startandroid.vocabulary.ui.dictionary.list.mvp

import ru.startandroid.vocabulary.base.mvp.MvpPresenter
import ru.startandroid.vocabulary.base.mvp.MvpView

interface WordListContract {

    interface View : MvpView {

    }


    interface Presenter : MvpPresenter<View> {

    }

}