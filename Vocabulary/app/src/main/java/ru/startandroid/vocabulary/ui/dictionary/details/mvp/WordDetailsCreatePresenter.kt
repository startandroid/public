package ru.startandroid.vocabulary.ui.dictionary.details.mvp

import io.reactivex.android.schedulers.AndroidSchedulers
import ru.startandroid.domain.model.Word
import ru.startandroid.domain.usecase.word.InsertWords
import ru.startandroid.vocabulary.base.mvp.BasePresenter

class WordDetailsCreatePresenter(val insertWords: InsertWords) : BasePresenter<WordDetailsContract.View>(), WordDetailsContract.Presenter {


    override fun onViewIsReady(firstTime: Boolean) {

    }

    override fun onSaveClick() {
        val word = Word()
        view?.fillWord(word)
        addDisposable(
                insertWords.execute(listOf(word))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe()
        )
        view?.closeScreen()
    }
}