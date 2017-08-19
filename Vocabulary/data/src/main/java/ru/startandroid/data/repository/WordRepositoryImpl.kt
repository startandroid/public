package ru.startandroid.data.repository

import io.reactivex.Observable
import io.reactivex.Scheduler
import ru.startandroid.data.database.dao.WordDao
import ru.startandroid.data.database.mapper.WordMapper
import ru.startandroid.domain.model.Word
import ru.startandroid.domain.repository.WordRepository

class WordRepositoryImpl(val wordDao: WordDao, val wordMapper: WordMapper, val dbScheduler: Scheduler): WordRepository {
    override fun getWords(): Observable<List<Word>> {
        // TODO make it easy (extension)
        return wordDao.getWords()
                .map { it.map { wordMapper.mapFromEntity(it) } }
                .subscribeOn(dbScheduler)
    }
}