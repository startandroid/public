package ru.startandroid.vocabulary.ui.dictionary.list.mvp

import android.os.Bundle
import ru.startandroid.vocabulary.R
import ru.startandroid.vocabulary.base.ui.BaseFragment

class WordListFragment : BaseFragment<WordListContract.Presenter>(), WordListContract.View {
    override fun getLayoutId() = R.layout.fragment_word_list


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attachView(this);
        presenter.viewIsReady();

    }

}