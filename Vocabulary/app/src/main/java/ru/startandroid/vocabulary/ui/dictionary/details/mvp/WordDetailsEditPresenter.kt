package ru.startandroid.vocabulary.ui.dictionary.details.mvp

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import ru.startandroid.domain.model.Word
import ru.startandroid.domain.usecase.word.GetWordById
import ru.startandroid.domain.usecase.word.UpdateWords
import ru.startandroid.vocabulary.base.mvp.BasePresenter

class WordDetailsEditPresenter(val updateWords: UpdateWords, val getWordById: GetWordById, val wordId: Int) : BasePresenter<WordDetailsContract.View>(), WordDetailsContract.Presenter {

    lateinit var word: Word

    override fun onViewIsReady(firstTime: Boolean) {
        if (firstTime) loadData()
    }

    private fun loadData() {
        addDisposable(
                getWordById.execute(wordId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            word = it
                            updateUi()
                        }, {
                            view?.closeScreen()
                        })
        )
    }

    private fun updateUi() {
        view?.showWord(word);
    }

    override fun onSaveClick() {
        view?.fillWord(word)
        addDisposable(
                updateWords.execute(listOf(word))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { Log.d("qweee", "ok") }
        )
        view?.closeScreen()
    }
}