package com.example.coin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * Класс для предоставления доступа к БД
 *
 */
@Database(entities = [Record::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun recordDatabase(): RecordDao
}

/**
 * Конвертер для типов даты-времени в БД
 */
object TiviTypeConverters {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    @JvmStatic
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}