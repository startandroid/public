package ru.startandroid.vocabulary.ui.dictionary.list.adapter

import android.support.v7.util.DiffUtil
import ru.startandroid.domain.model.Word

class WordDiffCallback(val oldData: List<Word>, val newData: List<Word>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData.get(oldItemPosition).id == newData.get(newItemPosition).id
    }

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData.get(oldItemPosition).areContentTheSame(newData.get(newItemPosition))
    }
}