package com.example.coin.domain

import com.example.coin.data.Record
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

/**
 * Юзкейс для получения всех записей
 *
 * @property recordRepository
 */
class GetAllRecordsUseCase @Inject constructor(
    private val recordRepository: RecordRepository
) {

    fun execute(): Flowable<List<Record>> {
        return recordRepository.getAll()
    }

}