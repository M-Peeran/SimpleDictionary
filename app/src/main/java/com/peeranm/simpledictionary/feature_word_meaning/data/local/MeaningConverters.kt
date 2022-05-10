package com.peeranm.simpledictionary.feature_word_meaning.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.peeranm.simpledictionary.feature_word_meaning.model.Meaning
import com.peeranm.simpledictionary.core.JasonParser

@ProvidedTypeConverter
class MeaningConverters(private val parser: JasonParser) {

    @TypeConverter
    fun fromMeaningJson(json: String): List<Meaning> {
        val typeToken = object : TypeToken<ArrayList<Meaning>>(){}
        return parser.fromJson<ArrayList<Meaning>>(json, typeToken.type) ?: emptyList()
    }

    @TypeConverter
    fun toMeaningJson(obj: List<Meaning>): String {
        val typeToken = object : TypeToken<ArrayList<Meaning>>(){}
        return parser.toJson(obj, typeToken.type) ?: "[]"
    }

}