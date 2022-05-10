package com.peeranm.simpledictionary.feature_word_meaning.data.remote.dto

data class DefinitionDto(
    val antonyms: List<String>? = null,
    val definition: String? = null,
    val example: String? = null,
    val synonyms: List<String>? = null
)