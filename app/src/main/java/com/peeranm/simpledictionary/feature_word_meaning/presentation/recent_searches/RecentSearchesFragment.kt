package com.peeranm.simpledictionary.feature_word_meaning.presentation.recent_searches

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.peeranm.simpledictionary.R
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.core.OnItemClickListener
import com.peeranm.simpledictionary.core.collectLatestWithLifecycle
import com.peeranm.simpledictionary.core.setActionBarTitle
import com.peeranm.simpledictionary.databinding.RecentSearchesFragmentBinding
import com.peeranm.simpledictionary.feature_word_meaning.utils.WordInfoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecentSearchesFragment : Fragment(), OnItemClickListener<WordInfo>, SearchView.OnQueryTextListener {

    private var _binding: RecentSearchesFragmentBinding? = null
    private val binding: RecentSearchesFragmentBinding
    get() = _binding!!

    private val viewModel: RecentSearchesViewModel by viewModels()
    private var adapter: WordInfoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecentSearchesFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setActionBarTitle(R.string.recent_searches)
        binding.bindList()
        collectLatestWithLifecycle(viewModel.recentSearches) { wordInfos ->
            if (wordInfos.isNotEmpty()) {
                binding.toggleRecentSearchesMessage(false)
                adapter?.submitList(wordInfos)
            }
        }
    }

    override fun onItemCLick(item: WordInfo, position: Int) {
        findNavController().navigate(
            RecentSearchesFragmentDirections.actionMainFragmentToWordDetailsFragment(item.id)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        val actionSearch = menu.findItem(R.id.actionSearchMeaning).actionView as SearchView
        actionSearch.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            findNavController().navigate(
                RecentSearchesFragmentDirections.actionMainFragmentToSearchResultFragment(query)
            )
        }
        return true
    }

    override fun onQueryTextChange(newText: String?) = false

    private fun RecentSearchesFragmentBinding.bindList() {
        adapter = WordInfoAdapter(this@RecentSearchesFragment)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listRecentSearches.adapter = adapter
        listRecentSearches.layoutManager = layoutManager
        listRecentSearches.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
    }

    private fun RecentSearchesFragmentBinding.toggleRecentSearchesMessage(showNow: Boolean = false) {
        textRecentSearchesMessage.visibility = if (showNow) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter?.onClear()
        adapter = null
        _binding = null
    }

}