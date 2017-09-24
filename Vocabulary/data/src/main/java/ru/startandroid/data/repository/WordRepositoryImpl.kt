package ru.startandroid.data.repository

import io.reactivex.*
import ru.startandroid.data.database.dao.WordDao
import ru.startandroid.data.database.mapper.WordMapper
import ru.startandroid.domain.model.Word
import ru.startandroid.domain.repository.WordRepository

class WordRepositoryImpl(private val wordDao: WordDao,
                         private val wordMapper: WordMapper,
                         private val dbScheduler: Scheduler): WordRepository {

    override fun getWord(wordId: Int): Single<Word> {
        return wordDao.getWord(wordId)
                .map { wordMapper.mapFromEntity(it) }
                .subscribeOn(dbScheduler)
    }

    override fun getWords(): Flowable<List<Word>> {
        // TODO make it easy (extension)
        return wordDao.getWords()
                .map { it.map { wordMapper.mapFromEntity(it) } }
                .subscribeOn(dbScheduler)
    }

    override fun insertWords(words: List<Word>): Completable {
        return Completable.fromCallable{wordDao.insertAll(wordMapper.mapListToEntity(words))}.subscribeOn(dbScheduler)
    }

    override fun updateWords(words: List<Word>): Completable {
        return Completable.fromCallable{wordDao.updateAll(wordMapper.mapListToEntity(words))}.subscribeOn(dbScheduler)
    }




}