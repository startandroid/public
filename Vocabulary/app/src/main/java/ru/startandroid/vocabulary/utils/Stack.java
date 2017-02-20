package ru.startandroid.vocabulary.utils;

import java.util.LinkedList;

public class Stack<T> {

    LinkedList<T> linkedList = new LinkedList<>();

    private final int size;

    public Stack(int size) {
        this.size = size;
    }

    public void add(T item) {
        linkedList.addFirst(item);
        if (linkedList.size() > size) {
            linkedList.removeLast();
        }
    }

    public boolean contains(T item) {
        return linkedList.contains(item);
    }

}
