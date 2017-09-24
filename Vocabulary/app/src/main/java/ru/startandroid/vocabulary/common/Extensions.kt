package ru.startandroid.vocabulary.common

import android.widget.EditText

//fun EditText.asString(): String = text.toString()

inline var EditText.string
    get() = text.toString()
    set(value) {setText(value)}



