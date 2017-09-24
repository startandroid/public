package ru.startandroid.vocabulary.ui.dictionary.list.adapter

import android.view.View
import android.widget.TextView
import butterknife.BindView
import ru.startandroid.domain.model.Word
import ru.startandroid.vocabulary.R
import ru.startandroid.vocabulary.base.adapter.BaseHolder
import ru.startandroid.vocabulary.base.adapter.OnItemClickListener

class WordHolder(itemView: View, onItemClickListener: OnItemClickListener?) : BaseHolder(itemView, onItemClickListener) {

    @BindView(R.id.value)
    lateinit var textViewValue: TextView

    fun bind(word: Word) {
        textViewValue.text = word.value
    }


}