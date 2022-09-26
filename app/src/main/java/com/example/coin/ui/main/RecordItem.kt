package com.example.coin.ui.main

import android.view.View
import com.example.coin.R
import com.example.coin.data.Record
import com.example.coin.databinding.RecordItemBinding
import com.xwray.groupie.viewbinding.BindableItem
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

/**
 * Класс одного элемента списка в списке записей
 *
 * @property record модель записи
 * @property onDeleteButtonPressed колбэк для удаления записи
 */
class RecordItem(
    private val record: Record,
    private val onDeleteButtonPressed: (Record) -> Unit
) : BindableItem<RecordItemBinding>() {

    override fun bind(viewBinding: RecordItemBinding, position: Int) {
        viewBinding.nameTextView.text = record.name
        viewBinding.valueTextView.text = "%.2f\u00A0₽".format(record.amount)
        viewBinding.dateTextView.text =
            record.date.format(
                DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(
                    Locale("ru")
                )
            )
        viewBinding.deleteButton.setOnClickListener { onDeleteButtonPressed(record) }
    }

    override fun getLayout(): Int {
        return R.layout.record_item
    }

    override fun initializeViewBinding(view: View): RecordItemBinding {
        return RecordItemBinding.bind(view)
    }


}