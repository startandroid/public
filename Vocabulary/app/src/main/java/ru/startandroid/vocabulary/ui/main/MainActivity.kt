package ru.startandroid.vocabulary.ui.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import ru.startandroid.vocabulary.R
import ru.startandroid.vocabulary.ui.dictionary.list.mvp.WordListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

    }

    @OnClick(R.id.dictionary)
    fun onDictionaryClick() {
        WordListActivity.start(this)
    }

}
