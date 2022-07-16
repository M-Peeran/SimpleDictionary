package com.peeranm.simpledictionary.feature_word_meaning.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peeranm.simpledictionary.feature_word_meaning.model.Meaning

@Entity(tableName = "word_info")
data class WordInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val word: String,
    val meanings: List<Meaning>,
    val phonetic: String,
    val audio: String
)