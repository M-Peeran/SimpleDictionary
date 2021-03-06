package com.peeranm.simpledictionary.feature_word_meaning.model

data class WordInfo(
    val id: Long = 0,
    val word: String,
    val meanings: List<Meaning>,
    val phonetic: String,
    val audio: String
)