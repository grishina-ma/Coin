package com.example.coin.domain

import com.example.coin.data.Record
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * Юзкейс для обновления записей
 *
 * @property repository
 */
class UpdateRecordUseCase @Inject constructor(private val repository: RecordRepository) {

    fun execute(record: Record): Completable {
        return repository.updateRecord(record)
    }

}