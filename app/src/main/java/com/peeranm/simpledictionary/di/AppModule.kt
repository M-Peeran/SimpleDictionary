package com.peeranm.simpledictionary.di

import android.app.Application
import com.peeranm.simpledictionary.feature_word_meaning.data.local.WordInfoDatabase
import com.peeranm.simpledictionary.feature_word_meaning.data.remote.RetrofitInstance
import com.peeranm.simpledictionary.feature_word_meaning.data.repository.WordInfoRepository
import com.peeranm.simpledictionary.feature_word_meaning.data.repository.WordInfoRepositoryImpl
import com.peeranm.simpledictionary.feature_word_meaning.use_cases.GetAllWordInfosUseCase
import com.peeranm.simpledictionary.feature_word_meaning.use_cases.GetWordInfoFromCacheUseCase
import com.peeranm.simpledictionary.feature_word_meaning.use_cases.GetWordInfoUseCase
import com.peeranm.simpledictionary.feature_word_meaning.use_cases.WordInfoUseCases
import com.peeranm.simpledictionary.feature_word_meaning.utils.WordInfoDtoMapper
import com.peeranm.simpledictionary.feature_word_meaning.utils.WordInfoEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WordInfoDatabase {
        return WordInfoDatabase(app)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): RetrofitInstance {
        return RetrofitInstance()
    }

    @Provides
    @Singleton
    fun provideRepository(
        database: WordInfoDatabase,
        retrofitInstance: RetrofitInstance
    ): WordInfoRepository {
        return WordInfoRepositoryImpl(
            wordInfoDao = database.getWordDao(),
            retrofitInstance = retrofitInstance,
            wordInfoDtoMapper = WordInfoDtoMapper(),
            wordInfoEntityMapper = WordInfoEntityMapper()
        )
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: WordInfoRepository): WordInfoUseCases {
        return WordInfoUseCases(
            getWordInfo = GetWordInfoUseCase(repository),
            getAllWordInfos = GetAllWordInfosUseCase(repository),
            getWordInfoFromCache = GetWordInfoFromCacheUseCase(repository)
        )
    }
}