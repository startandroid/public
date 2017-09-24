package ru.startandroid.vocabulary.ui.dictionary.list.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.startandroid.domain.model.Word
import ru.startandroid.vocabulary.R
import ru.startandroid.vocabulary.base.adapter.AdapterDataSource
import ru.startandroid.vocabulary.base.adapter.OnItemClickListener

class WordAdapter(private val adapterDataSource: AdapterDataSource<Word>, val onItemClickListener: OnItemClickListener?) : RecyclerView.Adapter<WordHolder>() {

    override fun onBindViewHolder(holder: WordHolder?, position: Int) {
        holder?.bind(adapterDataSource.getItem(position))
    }

    override fun getItemCount(): Int = adapterDataSource.getCount()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): WordHolder {
        return WordHolder(LayoutInflater.from(parent?.context).inflate(R.layout.word_list_item, parent, false), onItemClickListener)
    }
}