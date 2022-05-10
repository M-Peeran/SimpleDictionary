package com.peeranm.simpledictionary.feature_word_meaning.data.remote

import com.peeranm.simpledictionary.feature_word_meaning.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApi {

    @GET("/api/v2/entries/{languageCode}/{word}")
    suspend fun getWordInfo(
        @Path("word")
        word: String,
        @Path("languageCode")
        languageCode: String
    ): List<WordInfoDto>

}