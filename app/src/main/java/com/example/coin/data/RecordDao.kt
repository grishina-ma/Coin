package com.example.coin.data

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

/**
 * Интерфейс для последующей генерации объекта для доступа к таблице записей из БД
 *
 */
@Dao
interface RecordDao {

    /**
     * Метод запроса всех существующих записей
     *
     * @return Все записи из бд
     */
    @Query("SELECT * FROM record")
    fun getAll(): Flowable<List<Record>>

    /**
     * Метод для поиска записей по имени имени
     *
     * @param name запрос, поиск по подстроке
     * @return все аккаунты, удовлетворяющие запросу
     */
    @Query("SELECT * FROM record where name like '%' || :name || '%'")
    fun searchByName(name: String): Flowable<List<Record>>

    /**
     * Метод для удаления записи
     *
     * @param record запись, которую нужно удалить
     * @return
     */
    @Delete
    fun delete(record: Record): Completable

    /**
     * Метод для обновления записи
     *
     * @param record запись, которую нужно обновить
     * @return
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(record: Record): Completable

    /**
     * Метод для вставки записи в БД
     *
     * @param records переменное число записей для вставки
     * @return
     */
    @Insert
    fun insert(vararg records: Record): Completable
}