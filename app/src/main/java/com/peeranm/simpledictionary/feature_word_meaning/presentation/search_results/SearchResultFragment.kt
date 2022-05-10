package com.peeranm.simpledictionary.feature_word_meaning.presentation.search_results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.peeranm.simpledictionary.R
import com.peeranm.simpledictionary.core.DataState
import com.peeranm.simpledictionary.core.OnItemClickListener
import com.peeranm.simpledictionary.core.collectWithLifecycle
import com.peeranm.simpledictionary.core.setActionBarTitle
import com.peeranm.simpledictionary.databinding.SearchResultFragmentBinding
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.feature_word_meaning.utils.SearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchResultFragment : Fragment(), OnItemClickListener<WordInfo> {

    private var _binding: SearchResultFragmentBinding? = null
    private val binding: SearchResultFragmentBinding
    get() = _binding!!

    private val viewModel: SearchResultViewModel by viewModels()
    private var adapter: SearchResultAdapter? = null
    private var searchResultsJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchResultFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle(R.string.search_results)

        adapter = SearchResultAdapter(this@SearchResultFragment)
        binding.apply {
            listSearchResults.adapter = adapter
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            listSearchResults.layoutManager = layoutManager
            listSearchResults.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    layoutManager.orientation
                )
            )
        }

        searchResultsJob = collectWithLifecycle(viewModel.resultWordInfos) { resultState ->
            when (resultState) {
                is DataState.Loading -> {
                    showOrHideProgressbar(wannaShow = true)
                    if (resultState.data != null) {
                        adapter?.submitList(resultState.data)
                        showOrHideProgressbar(wannaShow = false)
                    }
                }
                is DataState.Error -> {
                    if (resultState.data != null) { adapter?.submitList(resultState.data) }
                    Snackbar.make(view, resultState.message, Snackbar.LENGTH_SHORT).show()
                    showOrHideProgressbar(wannaShow = false)
                }
                is DataState.Success -> {
                    adapter?.submitList(resultState.data)
                    showOrHideProgressbar(wannaShow = false)
                }
                else -> Unit
            }
        }
    }

    override fun onItemCLick(item: WordInfo, position: Int) {
        findNavController().navigate(
            SearchResultFragmentDirections.actionSearchResultFragmentToWordDetailsFragment(item.id)
        )
    }

    private suspend fun showOrHideProgressbar(wannaShow: Boolean) {
        withContext(Dispatchers.Main) {
            if (wannaShow) binding.progressBar.visibility = View.VISIBLE
            else {
                delay(500L)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchResultsJob = null
        adapter?.onClear()
        adapter = null
        _binding = null
    }
}