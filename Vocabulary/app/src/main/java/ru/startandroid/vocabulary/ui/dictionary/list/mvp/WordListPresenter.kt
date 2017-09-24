package ru.startandroid.vocabulary.ui.dictionary.list.mvp

import android.support.v7.util.DiffUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.startandroid.domain.model.Word
import ru.startandroid.domain.usecase.word.GetWords
import ru.startandroid.vocabulary.base.adapter.AdapterDataSource
import ru.startandroid.vocabulary.base.mvp.BasePresenter
import ru.startandroid.vocabulary.ui.dictionary.list.adapter.WordDiffCallback

class WordListPresenter(private val getWords: GetWords) : BasePresenter<WordListContract.View>(), WordListContract.Presenter, AdapterDataSource<Word> {
    override fun onItemClick(position: Int) {
        view?.editWord(data[position].id)
    }

    private var data: List<Word> = emptyList()

    override fun onViewIsReady(firstTime: Boolean) {
        if (firstTime) loadData()
    }

    private fun loadData() {
        addDisposable(
                getWords.execute()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ updateData(it) })
        )
    }

    private fun updateData(data: List<Word>) {
        val diffResult = DiffUtil.calculateDiff(WordDiffCallback(this.data, data))
        this.data = data
        view?.updateData(diffResult)

    }

    override fun getCount(): Int = data.size

    override fun getItem(position: Int): Word = data[position]


}