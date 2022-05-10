package com.peeranm.simpledictionary.feature_word_meaning.data.remote

import com.peeranm.simpledictionary.core.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dictionaryApi: DictionaryApi by lazy { retrofit.create(DictionaryApi::class.java) }

}