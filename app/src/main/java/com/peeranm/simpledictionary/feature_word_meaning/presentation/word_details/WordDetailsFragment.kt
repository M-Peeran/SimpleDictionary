package com.peeranm.simpledictionary.feature_word_meaning.presentation.word_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.peeranm.simpledictionary.R
import com.peeranm.simpledictionary.core.collectLatestWithLifecycle
import com.peeranm.simpledictionary.core.collectWithLifecycle
import com.peeranm.simpledictionary.core.setActionBarTitle
import com.peeranm.simpledictionary.databinding.WordDetailsFragmentBinding
import com.peeranm.simpledictionary.feature_word_meaning.model.Definition
import com.peeranm.simpledictionary.feature_word_meaning.model.Meaning
import com.peeranm.simpledictionary.feature_word_meaning.utils.WordDetailsAdapter
import com.peeranm.simpledictionary.feature_word_meaning.utils.dummyData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WordDetailsFragment : Fragment() {

    private var _binding: WordDetailsFragmentBinding? = null
    private val binding: WordDetailsFragmentBinding
    get() = _binding!!

    private val viewModel: WordDetailsViewModel by viewModels()
    private var wordDetailsAdapter: WordDetailsAdapter? = null
    private var wordInfoJob: Job? = null

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

        wordDetailsAdapter = WordDetailsAdapter()
        binding.listMeanings.setAdapter(wordDetailsAdapter)

        wordInfoJob = collectWithLifecycle(viewModel.wordInfo) { wordInfo ->
            binding.textSelcetedWord.text = wordInfo.word
            binding.textWordPhonetic.text = wordInfo.phonetic
            val data = mutableMapOf<String, List<Definition>>()
            wordInfo.meanings.forEach { meaning -> data[meaning.partOfSpeech] = meaning.definitions }
            wordDetailsAdapter?.submitData(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        wordInfoJob = null
        wordDetailsAdapter = null
        _binding = null
    }

}