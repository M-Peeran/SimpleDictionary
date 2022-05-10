package com.peeranm.simpledictionary.feature_word_meaning.data.remote.dto

data class WordInfoDto(
    val meanings: List<MeaningDto>? = null,
    val phonetic: String? = null,
    val phonetics: List<PhoneticDto>? = null,
    val sourceUrls: List<String>? = null,
    val word: String? = null
)