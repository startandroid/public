package ru.startandroid.vocabulary.base.adapter

interface AdapterDataSource<T> {
    fun getCount(): Int
    fun getItem(position: Int): T

}