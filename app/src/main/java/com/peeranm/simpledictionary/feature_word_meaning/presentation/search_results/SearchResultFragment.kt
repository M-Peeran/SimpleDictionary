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
import com.peeranm.simpledictionary.R
import com.peeranm.simpledictionary.core.*
import com.peeranm.simpledictionary.databinding.SearchResultFragmentBinding
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.feature_word_meaning.utils.SearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment(), OnItemClickListener<WordInfo> {

    private var _binding: SearchResultFragmentBinding? = null
    private val binding: SearchResultFragmentBinding
    get() = _binding!!

    private val viewModel: SearchResultViewModel by viewModels()
    private var adapter: SearchResultAdapter? = null

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
        binding.bindList()

        collectWithLifecycle(viewModel.resultWordInfos) { data ->
            if (data.isNotEmpty()) {
                adapter?.submitList(data)
                binding.toggleProgressbar(showNow = false)
            }
        }

        collectWithLifecycle(viewModel.message) { message ->
            if (message.isNotEmpty()) {
                binding.toggleProgressbar(showNow = false)
                showToast(message)
            }
        }
    }

    override fun onItemCLick(item: WordInfo, position: Int) {
        findNavController().navigate(
            SearchResultFragmentDirections.actionSearchResultFragmentToWordDetailsFragment(item.id)
        )
    }

    private fun SearchResultFragmentBinding.bindList() {
        adapter = SearchResultAdapter(this@SearchResultFragment)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listSearchResults.adapter = adapter
        listSearchResults.layoutManager = layoutManager
        listSearchResults.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
    }

    private fun SearchResultFragmentBinding.toggleProgressbar(showNow: Boolean) {
        progressBar.visibility = if (showNow) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter?.onClear()
        adapter = null
        _binding = null
    }
}