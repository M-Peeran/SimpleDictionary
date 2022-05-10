package com.peeranm.simpledictionary.feature_word_meaning.use_cases

import com.peeranm.simpledictionary.feature_word_meaning.data.repository.WordInfoRepository
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.core.Constants.Companion.LANG_CODE
import com.peeranm.simpledictionary.core.DataState
import kotlinx.coroutines.flow.Flow

class GetWordInfoUseCase(private val repository: WordInfoRepository) {
    suspend operator fun invoke(word: String, languageCode: String = LANG_CODE)
    : Flow<DataState<List<WordInfo>>> {
        return repository.getWordInfo(word, languageCode)
    }
}