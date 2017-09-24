package ru.startandroid.vocabulary.ui.dictionary.details

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.startandroid.vocabulary.R
import ru.startandroid.vocabulary.common.EXTRA_ACTION
import ru.startandroid.vocabulary.common.WordDetailsAction
import ru.startandroid.vocabulary.ui.dictionary.details.mvp.WordDetailsFragment
import ru.startandroid.vocabulary.ui.dictionary.list.WordListActivity

class WordDetailsActivity : AppCompatActivity() {

    companion object {
        private fun start(context: Context, action: WordDetailsAction) {
            val intent = Intent(context, WordDetailsActivity::class.java)
            intent.putExtra(EXTRA_ACTION, action)
            context.startActivity(intent)
        }

        fun createWord(context: Context) {
            start(context, WordDetailsAction.Create)
        }

        fun editWord(context: Context, id: Int) {
            start(context, WordDetailsAction.Edit(id))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_details)

        var action = intent.getSerializableExtra(EXTRA_ACTION) as WordDetailsAction

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, WordDetailsFragment.newInstance(action)).commit()
        }
    }
}
