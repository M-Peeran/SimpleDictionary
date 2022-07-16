package com.peeranm.simpledictionary.feature_word_meaning.presentation.recent_searches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.feature_word_meaning.use_cases.WordInfoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val wordInfoUseCases: WordInfoUseCases) : ViewModel() {

    private val _recentSearches = MutableStateFlow<List<WordInfo>>(emptyList())
    val recentSearches: StateFlow<List<WordInfo>> = _recentSearches

    init {
        viewModelScope.launch {
            wordInfoUseCases.getAllWordInfos().collect {
                _recentSearches.value = it
            }
        }
    }



}