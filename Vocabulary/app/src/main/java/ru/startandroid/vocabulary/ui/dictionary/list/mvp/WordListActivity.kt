package ru.startandroid.vocabulary.ui.dictionary.list.mvp

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ru.startandroid.vocabulary.R

class WordListActivity : AppCompatActivity() {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, WordListActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dictionary)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, WordListFragment()).commit()
        }

    }
}
