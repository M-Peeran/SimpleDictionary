package com.peeranm.simpledictionary.feature_word_meaning.model

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String
)