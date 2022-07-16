package com.peeranm.simpledictionary.feature_word_meaning.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.peeranm.simpledictionary.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}