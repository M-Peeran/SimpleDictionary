package com.peeranm.simpledictionary.feature_word_meaning.presentation.recent_searches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.coroutines.Job

@AndroidEntryPoint
class RecentSearchesFragment : Fragment(), OnItemClickListener<WordInfo> {

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
        setActionBarTitle(R.string.app_name)
        binding.bindList()

        // Replacing this with search in action bar, hence not refactored
        binding.apply {
            btnSearch.setOnClickListener {
                val searchText = etextSearch.text.toString()
                findNavController().navigate(
                    RecentSearchesFragmentDirections.actionMainFragmentToSearchResultFragment(searchText)
                )
            }
        }
        collectLatestWithLifecycle(viewModel.recentSearches) { adapter?.submitList(it) }
    }

    override fun onItemCLick(item: WordInfo, position: Int) {
        findNavController().navigate(
            RecentSearchesFragmentDirections.actionMainFragmentToWordDetailsFragment(item.id)
        )
    }

    private fun RecentSearchesFragmentBinding.bindList() {
        adapter = WordInfoAdapter(this@RecentSearchesFragment)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        listRecentSearches.adapter = adapter
        listRecentSearches.layoutManager = layoutManager
        listRecentSearches.addItemDecoration(DividerItemDecoration(requireContext(), layoutManager.orientation))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter?.onClear()
        adapter = null
        _binding = null
    }

}