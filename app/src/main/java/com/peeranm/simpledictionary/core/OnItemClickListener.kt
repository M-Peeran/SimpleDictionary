package com.peeranm.simpledictionary.core

interface OnItemClickListener<T> {
    fun onItemCLick(item: T, position: Int)
}