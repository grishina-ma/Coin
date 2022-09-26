package com.example.coin.domain

import com.example.coin.data.Record
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Интерфейс репозитория для доступа к данным
 *
 */
interface RecordRepository {
    /**
     * Метод для получения всех записей
     *
     * @return
     */
    fun getAll(): Flowable<List<Record>>

    /**
     * Метод для вставки новой записи
     *
     * @param record запись
     * @return
     */
    fun insertNewRecord(record: Record): Completable

    /**
     * Метод для обновления существующей записи
     *
     * @param record запись
     * @return
     */
    fun updateRecord(record: Record): Completable

    /**
     * Метод для удаления записи
     *
     * @param record запись
     * @return
     */
    fun deleteRecord(record: Record): Completable

    /**
     * Метод для поиска по названию записи
     *
     * @param searchRequest поисковой запрос
     * @return список всех записей, для которых запрос является подстрокой
     */
    fun searchByName(searchRequest: String): Flowable<List<Record>>
}