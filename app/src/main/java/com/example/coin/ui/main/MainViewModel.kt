package com.example.coin.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.coin.data.Record
import com.example.coin.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.OffsetDateTime
import javax.inject.Inject

/**
 * Класс состояния основного экрана
 *
 * @property records записи текущего состояния
 * @property isLoading происходит ли сейчас загрузка
 * @property error ошибка
 */
data class MainViewModelState(
    val records: List<Record>,
    val isLoading: Boolean,
    val error: Throwable?
) {
    companion object {
        fun empty() = MainViewModelState(
            records = emptyList(),
            isLoading = true,
            error = null
        )

        fun error(e: Throwable) = MainViewModelState(
            error = e,
            records = emptyList(),
            isLoading = false,
        )
    }
}

/**
 * ViewModel основного экрана
 *
 * @property deleteRecordUseCase
 * @property getAllRecordsUseCase
 * @property searchRecordUseCase
 * @property updateRecordUseCase
 * @property insertRecordUseCase
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val deleteRecordUseCase: DeleteRecordUseCase,
    private val getAllRecordsUseCase: GetAllRecordsUseCase,
    private val searchRecordUseCase: SearchRecordUseCase,
    private val updateRecordUseCase: UpdateRecordUseCase,
    private val insertRecordUseCase: InsertRecordUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val stateFlow = MutableStateFlow(MainViewModelState.empty())

    fun init() {
        compositeDisposable.add(getAllRecordsUseCase.execute().doOnEach {
            stateFlow.compareAndSet(
                stateFlow.value,
                stateFlow.value.copy(records = it.value!!, isLoading = false, error = null)
            )
        }.doOnError {
            stateFlow.compareAndSet(stateFlow.value, MainViewModelState.error(it))
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe())
    }

    fun search(searchRequest: String) {
        compositeDisposable.add(searchRecordUseCase.execute(searchRequest).doOnEach {
            stateFlow.compareAndSet(
                stateFlow.value,
                stateFlow.value.copy(
                    records = it.value!!,
                    isLoading = false,
                    error = null,
                )
            )
        }.doOnError {
            stateFlow.compareAndSet(stateFlow.value, MainViewModelState.error(it))
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe())
    }

    fun update(amount: String, recordName: String) {
        compositeDisposable.add(
            updateRecordUseCase.execute(
                Record(
                    uid = 0,
                    name = recordName,
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    date = OffsetDateTime.now()
                )
            ).doOnComplete {
                Log.d("d", "Updated")
            }.doOnError {
                Log.e("d", "update", it)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        )
    }

    fun delete(record: Record) {
        compositeDisposable.add(
            deleteRecordUseCase.execute(
                record
            ).doOnComplete {
                Log.d("d", "Deleted")
            }.doOnError {
                Log.e("d", "delete", it)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe()
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}