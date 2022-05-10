package com.peeranm.simpledictionary.feature_word_meaning.use_cases

import com.peeranm.simpledictionary.feature_word_meaning.data.repository.WordInfoRepository
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import kotlinx.coroutines.flow.Flow

class GetAllWordInfosUseCase(private val repository: WordInfoRepository) {
    operator fun invoke(): Flow<List<WordInfo>> {
        return repository.getAllWordInfo()
    }
}