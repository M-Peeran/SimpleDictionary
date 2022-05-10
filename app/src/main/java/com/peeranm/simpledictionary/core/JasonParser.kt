package com.peeranm.simpledictionary.core

import java.lang.reflect.Type

interface JasonParser {
    fun <T> fromJson(json: String, type: Type): T?
    fun <T> toJson(obj: T, type: Type): String?
}