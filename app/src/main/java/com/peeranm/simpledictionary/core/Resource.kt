package com.peeranm.simpledictionary.core


sealed class Resource<T> {
    class Loading<T>(val data: T? = null) : Resource<T>()
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val data: T? = null, val message: String) : Resource<T>()
    class None<T> : Resource<T>()
}