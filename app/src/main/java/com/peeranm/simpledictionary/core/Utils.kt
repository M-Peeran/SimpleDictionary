package com.peeranm.simpledictionary.core

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun getDummyWordInfo() = WordInfo(-1, "", emptyList(), "", "")

fun Fragment.setActionBarTitle(@StringRes stringResId: Int) {
    (this.requireActivity() as AppCompatActivity).supportActionBar?.title = this.getString(stringResId)
}

fun Fragment.showToast(message: String, duration: Int =  Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}

fun <T> LifecycleOwner.collectWithLifecycle(flow: Flow<T>, collect: suspend (T) -> Unit) =
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect(collect)
        }
    }

fun <T> LifecycleOwner.collectLatestWithLifecycle(flow: Flow<T>, collect: suspend (T) -> Unit) =
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }