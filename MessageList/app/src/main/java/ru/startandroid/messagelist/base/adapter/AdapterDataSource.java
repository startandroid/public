package ru.startandroid.messagelist.base.adapter;

public interface AdapterDataSource<T> {
    T getItem(int position);
    int getItemCount();
}
