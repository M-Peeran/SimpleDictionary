package com.peeranm.simpledictionary.core

sealed class DataState<T>(data: T? = null, message: String? = null) {
    class Loading<T>(val data: T? = null) : DataState<T>(data)
    class Success<T>(val data: T) : DataState<T>(data)
    class Error<T>(val data: T? = null, val message: String) : DataState<T>(data, message)
    class None<T> : DataState<T>()
}