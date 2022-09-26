package com.example.coin.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.coin.databinding.FragmentCreateBinding
import com.example.coin.util.BaseBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.subscribe

/**
 * Экран добавления новой записи
 *
 */
@AndroidEntryPoint
class NewRecordBottomSheetFragment :
    BaseBottomSheetFragment<FragmentCreateBinding, NewRecordViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    handleState(it)
                }
            }
        }
        binding.submitButton.setOnClickListener {
            with(binding) {
                viewModel.create(
                    nameEditText.text.toString(),
                    amountEditText.text.toString(),
                )
            }
        }
    }

    private fun handleState(state: NewRecordState) {
        when {
            state.created -> {
                dismiss()
            }
            state.error != null -> {
                Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_LONG).show()
            }
        }
    }

}