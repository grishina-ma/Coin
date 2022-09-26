package com.example.coin.domain

import com.example.coin.data.Record
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

/**
 * Юзкейс для поиска записи
 *
 * @property repository
 */
class SearchRecordUseCase @Inject constructor(private val repository: RecordRepository) {

    fun execute(searchRequest: String): Flowable<List<Record>> {
        return repository.searchByName(searchRequest)
    }

}