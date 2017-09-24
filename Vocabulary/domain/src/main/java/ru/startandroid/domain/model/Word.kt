package ru.startandroid.domain.model


data class Word (var id: Int = 0,
                 var value: String = "",
                 var translate: String = "",
                 var sample: String = "",
                 var definition: String = "",
                 var rememberedCount: Int = 0,
                 var added: Long = 0) {

    // TODO move to separate class?

    fun areContentTheSame(word: Word): Boolean {
        return value.equals(word.value) &&
                translate.equals(word.translate) &&
                sample.equals(word.sample) &&
                definition.equals(word.definition)
    }

}

