package com.peeranm.simpledictionary.feature_word_meaning.presentation.word_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.peeranm.simpledictionary.R
import com.peeranm.simpledictionary.core.collectWithLifecycle
import com.peeranm.simpledictionary.core.setActionBarTitle
import com.peeranm.simpledictionary.core.showToast
import com.peeranm.simpledictionary.databinding.WordDetailsFragmentBinding
import com.peeranm.simpledictionary.feature_word_meaning.model.Definition
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.feature_word_meaning.utils.WordDetailsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordDetailsFragment : Fragment() {

    private var _binding: WordDetailsFragmentBinding? = null
    private val binding: WordDetailsFragmentBinding
    get() = _binding!!

    private val viewModel: WordDetailsViewModel by viewModels()
    private var wordDetailsAdapter: WordDetailsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WordDetailsFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarTitle(R.string.word_details)
        binding.bindExpandableList()

        collectWithLifecycle(viewModel.wordInfo) { binding.bindWordInfo(it) }

        collectWithLifecycle(viewModel.message) { message ->
            if (message.isNotEmpty()) showToast(message)
        }
    }

    private fun WordDetailsFragmentBinding.bindWordInfo(wordInfo: WordInfo) {
        textSelcetedWord.text = wordInfo.word
        textWordPhonetic.text = wordInfo.phonetic
        val data = mutableMapOf<String, List<Definition>>()
        wordInfo.meanings.forEach { meaning -> data[meaning.partOfSpeech] = meaning.definitions }
        wordDetailsAdapter?.submitData(data)
    }

    private fun WordDetailsFragmentBinding.bindExpandableList() {
        wordDetailsAdapter = WordDetailsAdapter()
        listMeanings.setAdapter(wordDetailsAdapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        wordDetailsAdapter = null
        _binding = null
    }

}