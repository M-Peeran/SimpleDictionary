package com.peeranm.simpledictionary.feature_word_meaning.data.repository

import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.core.DataState
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    suspend fun getWordInfoById(id: Int): WordInfo
    suspend fun getWordInfo(word: String, languageCode: String): Flow<DataState<List<WordInfo>>>
    fun getAllWordInfo(): Flow<List<WordInfo>>

}