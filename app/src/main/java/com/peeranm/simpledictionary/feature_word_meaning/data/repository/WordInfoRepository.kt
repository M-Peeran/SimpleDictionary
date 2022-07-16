package com.peeranm.simpledictionary.feature_word_meaning.data.repository

import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.core.Resource
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    suspend fun getWordInfoById(id: Long): WordInfo
    fun getWordInfo(word: String, languageCode: String): Flow<Resource<List<WordInfo>>>
    fun getAllWordInfo(): Flow<List<WordInfo>>

}