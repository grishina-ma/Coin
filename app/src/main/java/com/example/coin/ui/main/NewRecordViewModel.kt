package com.example.coin.ui.main

import androidx.lifecycle.ViewModel
import com.example.coin.data.Record
import com.example.coin.domain.InsertRecordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.OffsetDateTime
import javax.inject.Inject

/**
 * Класс состояния экрана создания новой записи
 *
 * @property created создана ли запись
 * @property error ошибка
 */
data class NewRecordState(
    val created: Boolean,
    val error: Throwable?
)

@HiltViewModel
class NewRecordViewModel @Inject constructor(
    private val createUseCase: InsertRecordUseCase
): ViewModel() {

    val stateFlow = MutableStateFlow(NewRecordState(false, null))

    fun create(name: String, amount: String) {
        createUseCase.execute(Record(
            uid = 0,
            name = name,
            amount = amount.toDoubleOrNull() ?: 0.0,
            date = OffsetDateTime.now()
        )).doOnComplete {
            stateFlow.compareAndSet(stateFlow.value, NewRecordState(true, null))
        }.doOnError {
            stateFlow.compareAndSet(stateFlow.value, NewRecordState(false, it))
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

}