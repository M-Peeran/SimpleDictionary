package com.peeranm.simpledictionary.feature_word_meaning.presentation.word_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.simpledictionary.core.getDummyWordInfo
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.feature_word_meaning.use_cases.WordInfoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailsViewModel @Inject constructor(
    private val wordInfoUseCases: WordInfoUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _wordInfo = MutableStateFlow(getDummyWordInfo())
    val wordInfo: StateFlow<WordInfo> = _wordInfo

    init {
        viewModelScope.launch {
            val wordInfoId = savedStateHandle.get<Int>("wordInfoId") ?: -1
            if (wordInfoId != -1) {
                _wordInfo.value = wordInfoUseCases.getWordInfoFromCache(wordInfoId)
            }
        }
    }

}