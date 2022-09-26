package com.example.coin.data

import com.example.coin.domain.RecordRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

/**
 * Реализация репозитория для локальной БД
 *
 * @property recordDao объект для доступа в БД
 */
class RecordRepositoryImpl @Inject constructor(
    private val recordDao: RecordDao
) : RecordRepository {
    override fun getAll(): Flowable<List<Record>> {
        return recordDao.getAll()
    }

    override fun searchByName(searchRequest: String): Flowable<List<Record>> {
        return recordDao.searchByName(searchRequest)
    }

    override fun insertNewRecord(record: Record): Completable {
        return recordDao.insert(record)
    }

    override fun updateRecord(record: Record): Completable {
        return recordDao.update(record)
    }

    override fun deleteRecord(record: Record): Completable {
        return recordDao.delete(
            record
        )
    }
}