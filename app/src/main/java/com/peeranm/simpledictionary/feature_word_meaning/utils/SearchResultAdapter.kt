package com.peeranm.simpledictionary.feature_word_meaning.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.peeranm.simpledictionary.core.OnItemClickListener
import com.peeranm.simpledictionary.databinding.SearchResultItemBinding
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo

class SearchResultAdapter(private var listener: OnItemClickListener<WordInfo>?)
    : RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder>() {

    inner class SearchResultHolder(val binding: SearchResultItemBinding)
        : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init { binding.root.setOnClickListener(this) }

        override fun onClick(view: View?) {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener?.onItemCLick(
                    item = differ.currentList[adapterPosition],
                    position = adapterPosition
                )
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<WordInfo>() {
        override fun areItemsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: WordInfo, newItem: WordInfo): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultHolder {
        return SearchResultHolder(
            SearchResultItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchResultHolder, position: Int) {
        holder.binding.apply {
            val wordInfo = differ.currentList[position]
            textWord.text = wordInfo.word
            textPhonetic.text = wordInfo.phonetic
            textPOS.text = wordInfo.meanings[0].partOfSpeech
        }
    }

    fun submitList(list: List<WordInfo>) {
        differ.submitList(list)
    }

    // Release resources
    fun onClear() {
        listener = null
    }
}