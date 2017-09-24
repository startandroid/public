package ru.startandroid.vocabulary.ui.dictionary.list.mvp

import android.support.v7.util.DiffUtil
import ru.startandroid.vocabulary.base.mvp.MvpPresenter
import ru.startandroid.vocabulary.base.mvp.MvpView

interface WordListContract {

    interface View : MvpView {
        fun updateData(diffResult: DiffUtil.DiffResult)
        fun editWord(id: Int)
    }


    interface Presenter : MvpPresenter<View> {
        fun onItemClick(position: Int)
    }

}