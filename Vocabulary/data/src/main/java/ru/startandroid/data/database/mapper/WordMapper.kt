package ru.startandroid.data.database.mapper

import ru.startandroid.data.database.model.WordEntity
import ru.startandroid.domain.model.Word

class WordMapper {

    fun mapFromEntity(wordEntity: WordEntity) = wordEntity.run { Word(id, value, translate, sample, definition, rememberedCount, added) }


    fun mapToEntity(word: Word) = word.run { WordEntity(id, value, translate, sample, definition, rememberedCount, added) }
    fun mapListToEntity(words: List<Word>) = words.map { mapToEntity(it) }

}