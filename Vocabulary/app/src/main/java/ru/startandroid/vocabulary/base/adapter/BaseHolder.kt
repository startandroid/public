package ru.startandroid.vocabulary.base.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import butterknife.ButterKnife

open class BaseHolder(itemView: View, onItemClickListener: OnItemClickListener?): RecyclerView.ViewHolder(itemView) {

    init {
        ButterKnife.bind(this, itemView)
        itemView.setOnClickListener { onItemClickListener?.onItemClick(adapterPosition) }
    }

}