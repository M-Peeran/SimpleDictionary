package com.peeranm.simpledictionary.feature_word_meaning.use_cases

import com.peeranm.simpledictionary.feature_word_meaning.data.repository.WordInfoRepository
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo


class GetWordInfoFromCacheUseCase(private val repository: WordInfoRepository) {
    suspend operator fun invoke(id: Long): WordInfo {
       return repository.getWordInfoById(id)
    }
}