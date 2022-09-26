package com.example.coin.domain

import com.example.coin.data.Record
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

/**
 * Юзкейс для удаления записи
 *
 * @property recordRepository
 */
class DeleteRecordUseCase @Inject constructor(private val recordRepository: RecordRepository) {

    fun execute(record: Record): Completable {
        return recordRepository.deleteRecord(record)
    }
}