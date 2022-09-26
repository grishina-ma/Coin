package com.example.coin.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coin.R
import com.example.coin.data.Record
import com.example.coin.databinding.FragmentMainBinding
import com.example.coin.util.BaseFragment
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Основной экран приложения
 *
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>(),
    Toolbar.OnMenuItemClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val adapter = GroupieAdapter()

    /**
     * Здесь происходят подписки на обновления состояния и добавляются колбэки
     *
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect {
                    handleState(it)
                }
            }
        }

        binding.searchToolbar.setOnMenuItemClickListener(this)
        (binding.searchToolbar.menu.findItem(R.id.action_search).actionView as? SearchView)?.let {
            search(
                it
            )
        }
        binding.createButton.setOnClickListener {
            NewRecordBottomSheetFragment().show(parentFragmentManager, "NewRecord")
        }

        viewModel.init()
    }

    /**
     * Метод для обработки состояния
     *
     * @param state состояние экрана
     */
    private fun handleState(state: MainViewModelState) {
        when {
            state.isLoading -> {
                showLoading()
            }
            state.error != null -> {
                showError(state.error)
            }
            state.records.isEmpty() -> {
                showEmpty()
            }
            else -> {
                showContent(state.records)
            }
        }
    }

    /**
     * Метод для показа загрузки
     *
     */
    private fun showLoading() {
        binding.recyclerView.gone()
        binding.emptyMessage.gone()
        binding.errorMessage.gone()
        binding.createButton.gone()
        binding.searchToolbar.gone()
        binding.progressBar.visible()
    }

    /**
     * Метод для показа пустого состояния
     *
     */
    private fun showEmpty() {
        binding.recyclerView.gone()
        binding.emptyMessage.visible()
        binding.errorMessage.gone()
        binding.progressBar.gone()
        binding.createButton.visible()
        binding.searchToolbar.visible()
    }

    /**
     * Метод для показа экрана ошибки
     *
     * @param throwable ошибка
     */
    private fun showError(throwable: Throwable) {
        binding.recyclerView.gone()
        binding.emptyMessage.gone()
        binding.errorMessage.visible()
        binding.progressBar.gone()
        binding.errorMessage.error = throwable.message
    }

    /**
     * Метод для отрисовки контента
     * Здесь создаются и добавляются новые записи в список
     *
     * @param records
     */
    private fun showContent(records: List<Record>) {
        binding.recyclerView.visible()
        binding.emptyMessage.gone()
        binding.errorMessage.gone()
        binding.progressBar.gone()
        binding.createButton.visible()
        binding.searchToolbar.visible()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        adapter.clear()
        adapter.updateAsync(records.map { RecordItem(it) { record ->
            viewModel.delete(record)
        } })
    }

    /**
     * Метод для поиска по БД
     *
     * @param searchView вью поиска
     */
    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText.orEmpty())
                return true
            }
        })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                (item.actionView as? SearchView)?.let { search(it) }
            }
        }
        return false

    }

}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}