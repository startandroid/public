package ru.startandroid.vocabulary.common

import java.io.Serializable

const val EXTRA_ACTION = "action"

sealed class WordDetailsAction: Serializable {
    object Create: WordDetailsAction()
    data class Edit(val id: Int): WordDetailsAction()
}