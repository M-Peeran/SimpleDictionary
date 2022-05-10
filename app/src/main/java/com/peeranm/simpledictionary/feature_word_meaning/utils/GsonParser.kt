package com.peeranm.simpledictionary.feature_word_meaning.utils

import com.google.gson.Gson
import com.peeranm.simpledictionary.core.JasonParser
import java.lang.reflect.Type

class GsonParser(private val gson: Gson) : JasonParser {

    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json, type)
    }

    override fun <T> toJson(obj: T, type: Type): String? {
        return gson.toJson(obj, type)
    }

}