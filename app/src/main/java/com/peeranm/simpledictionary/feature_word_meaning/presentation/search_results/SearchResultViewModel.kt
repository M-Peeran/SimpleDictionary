package com.peeranm.simpledictionary.feature_word_meaning.presentation.search_results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.simpledictionary.core.Constants
import com.peeranm.simpledictionary.core.Resource
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.feature_word_meaning.use_cases.WordInfoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val wordInfoUseCases: WordInfoUseCases
) : ViewModel() {

    private val _resultWordInfos = MutableStateFlow<List<WordInfo>>(emptyList())
    val resultWordInfos: StateFlow<List<WordInfo>> = _resultWordInfos

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    private val _wordInfos = MutableStateFlow<List<WordInfo>>(emptyList())
    val wordInfos = _wordInfos.asStateFlow()

    init {
        val searchText = savedStateHandle.get<String>(Constants.SEARCH_TEXT)
        if (searchText  != null) findMeaning(searchText)
        else _message.value = "Search text is invalid!"
    }

    private fun findMeaning(searchText: String) {
        wordInfoUseCases.getWordInfo(searchText).onEach { resource ->
            when (resource) {
                is Resource.None -> Unit
                is Resource.Loading -> resource.data?.let { _resultWordInfos.value = it }
                is Resource.Success -> _resultWordInfos.value = resource.data
                is Resource.Error -> {
                    resource.data?.let { _resultWordInfos.value = it }
                    _message.value = resource.message
                }
            }
        }.launchIn(viewModelScope)
    }

}