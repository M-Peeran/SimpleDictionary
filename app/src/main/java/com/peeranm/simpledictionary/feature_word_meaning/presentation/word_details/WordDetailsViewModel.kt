package com.peeranm.simpledictionary.feature_word_meaning.presentation.word_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peeranm.simpledictionary.core.Constants
import com.peeranm.simpledictionary.core.getDummyWordInfo
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.feature_word_meaning.use_cases.WordInfoUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailsViewModel @Inject constructor(
    private val wordInfoUseCases: WordInfoUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _wordInfo = MutableStateFlow(getDummyWordInfo())
    val wordInfo: StateFlow<WordInfo> = _wordInfo

    private val _message = MutableStateFlow("")
    val message = _message.asStateFlow()

    init {
        val wordInfoId = savedStateHandle.get<Long>(Constants.ARG_WORD_INFO_ID)
        if (wordInfoId == null) _message.value = "WordInfoId is invalid!"
        else findWordInfo(wordInfoId)
    }

    private fun findWordInfo(wordInfoId: Long) {
        viewModelScope.launch {
            _wordInfo.value = wordInfoUseCases.getWordInfoFromCache(wordInfoId)
        }
    }

}