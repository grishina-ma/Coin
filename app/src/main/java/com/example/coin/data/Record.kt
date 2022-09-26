package com.example.coin.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.time.OffsetDateTime

/**
 * Класс записи, основная модель приложения
 *
 * @property uid id
 * @property date Дата создания записи
 * @property name Название записи
 * @property amount Количество денег, которое было потрачено
 */
@TypeConverters(TiviTypeConverters::class)
@Entity
data class Record(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "date") val date: OffsetDateTime,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "amount") val amount: Double
)
