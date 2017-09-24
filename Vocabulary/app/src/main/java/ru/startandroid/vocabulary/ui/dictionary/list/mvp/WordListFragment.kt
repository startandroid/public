package ru.startandroid.vocabulary.ui.dictionary.list.mvp

import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.BindView
import butterknife.OnClick
import ru.startandroid.domain.model.Word
import ru.startandroid.vocabulary.R
import ru.startandroid.vocabulary.base.adapter.AdapterDataSource
import ru.startandroid.vocabulary.base.adapter.OnItemClickListener
import ru.startandroid.vocabulary.base.ui.BaseFragment
import ru.startandroid.vocabulary.ui.dictionary.details.WordDetailsActivity
import ru.startandroid.vocabulary.ui.dictionary.list.adapter.WordAdapter

class WordListFragment : BaseFragment<WordListContract.Presenter>(), WordListContract.View {
    override fun editWord(id: Int) {
        WordDetailsActivity.editWord(context, id);
    }

    lateinit var wordAdapter: WordAdapter

    @BindView(R.id.data)
    lateinit var recyclerViewData: RecyclerView


    override fun getLayoutId() = R.layout.fragment_word_list

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.attachView(this)
        presenter.viewIsReady()
    }

    override fun initView(view: View?) {
        super.initView(view)
        var layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerViewData.layoutManager = layoutManager

        // TODO do it better
        val onItemClickListener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                presenter.onItemClick(position)
            }

        }

        wordAdapter = WordAdapter(presenter as AdapterDataSource<Word>, onItemClickListener)
        recyclerViewData.adapter = wordAdapter

    }

    override fun updateData(diffResult: DiffUtil.DiffResult) {
        diffResult.dispatchUpdatesTo(wordAdapter)
    }

    @OnClick(R.id.add)
    fun onAddClick() {
        WordDetailsActivity.createWord(context)
    }

}