package com.peeranm.simpledictionary.feature_word_meaning.presentation.search_results

import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo

sealed class WordDetailsEvents {
    class OnSearchText(val text: String) : WordDetailsEvents()
    class OnWordInfo(val wordInfo: WordInfo): WordDetailsEvents()
}