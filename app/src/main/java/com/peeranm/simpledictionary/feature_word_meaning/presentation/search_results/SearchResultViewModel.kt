package com.peeranm.simpledictionary.feature_word_meaning.presentation.search_results

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.simpledictionary.core.Resource
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.feature_word_meaning.use_cases.WordInfoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val wordInfoUseCases: WordInfoUseCases
) : ViewModel() {

    private val _resultWordInfos = MutableStateFlow<Resource<List<WordInfo>>>(Resource.None())
    val resultWordInfos: StateFlow<Resource<List<WordInfo>>> = _resultWordInfos

    init {
        val searchText = savedStateHandle.get<String>("searchText")
        if (searchText  != null) {
            viewModelScope.launch {
                wordInfoUseCases.getWordInfo(searchText)
                    .onEach { resultState -> _resultWordInfos.value = resultState }
                    .launchIn(this)
            }
        }
    }

}