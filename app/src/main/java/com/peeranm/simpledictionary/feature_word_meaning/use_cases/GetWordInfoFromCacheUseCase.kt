package com.peeranm.simpledictionary.feature_word_meaning.use_cases

import com.peeranm.simpledictionary.feature_word_meaning.data.repository.WordInfoRepository
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import kotlinx.coroutines.flow.Flow

class GetWordInfoFromCacheUseCase(private val repository: WordInfoRepository) {
    suspend operator fun invoke(id: Int): WordInfo {
       return repository.getWordInfoById(id)
    }
}