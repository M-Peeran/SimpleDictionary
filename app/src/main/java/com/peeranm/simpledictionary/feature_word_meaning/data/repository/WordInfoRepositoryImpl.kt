package com.peeranm.simpledictionary.feature_word_meaning.data.repository

import com.peeranm.simpledictionary.feature_word_meaning.data.local.WordInfoDao
import com.peeranm.simpledictionary.feature_word_meaning.data.remote.RetrofitInstance
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import com.peeranm.simpledictionary.core.Resource
import com.peeranm.simpledictionary.feature_word_meaning.utils.mappers.WordInfoDtoMapper
import com.peeranm.simpledictionary.feature_word_meaning.utils.mappers.WordInfoEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class WordInfoRepositoryImpl(
    private val wordInfoDao: WordInfoDao,
    private val retrofitInstance: RetrofitInstance,
    private val wordInfoDtoMapper: WordInfoDtoMapper,
    private val wordInfoEntityMapper: WordInfoEntityMapper
) : WordInfoRepository {

    override suspend fun getWordInfoById(id: Long): WordInfo {
        return wordInfoEntityMapper.fromEntity(
            wordInfoDao.getWordInfoById(id)
        )
    }

    override fun getWordInfo(word: String, languageCode: String)
    : Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())
        val oldWordInfos = wordInfoEntityMapper.fromEntities(wordInfoDao.getWordInfos(word))
        emit(Resource.Loading(oldWordInfos))
        try {
            val wordInfoDtos = retrofitInstance.dictionaryApi.getWordInfo(word, languageCode)
            val wordInfos = wordInfoDtoMapper.fromEntities(wordInfoDtos)
            wordInfoDao.deleteWordInfos(oldWordInfos.map { it.word })
            wordInfoDao.upsert(wordInfoEntityMapper.toEntities(wordInfos))
            val newWordInfos = wordInfoEntityMapper.fromEntities(wordInfoDao.getWordInfos(word))
            emit(Resource.Success(newWordInfos))
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    data = oldWordInfos,
                    message = "Couldn't reach server,check your internet connection"
                )
            )
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    data = oldWordInfos,
                    message = "The word : ${word.uppercase(Locale.getDefault())} can't be found! Please check the spelling!"
                )
            )
        }
    }

    override fun getAllWordInfo(): Flow<List<WordInfo>> {
        return wordInfoDao.getAllWordInfos().map {
            wordInfoEntityMapper.fromEntities(it)
        }
    }
}