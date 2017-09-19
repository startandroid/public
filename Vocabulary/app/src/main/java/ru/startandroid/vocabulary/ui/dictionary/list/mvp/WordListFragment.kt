package ru.startandroid.vocabulary.ui.dictionary.list.mvp

import android.os.Bundle
import android.widget.Toast
import butterknife.OnClick
import ru.startandroid.vocabulary.R
import ru.startandroid.vocabulary.base.ui.BaseFragment

class WordListFragment : BaseFragment<WordListContract.Presenter>(), WordListContract.View {
    override fun getLayoutId() = R.layout.fragment_word_list

    //@BindView(R.id.add)
    //lateinit var floatingActionButtonAdd : FloatingActionButton

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attachView(this);
        presenter.viewIsReady();

    }

    @OnClick(R.id.add)
    fun onAddClick() {
        Toast.makeText(activity, "click", Toast.LENGTH_LONG).show();
    }

}