package com.peeranm.simpledictionary.feature_word_meaning.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.peeranm.simpledictionary.core.OnItemClickListener
import com.peeranm.simpledictionary.databinding.RecentSearchItemBinding
import com.peeranm.simpledictionary.feature_word_meaning.model.WordInfo

class WordInfoAdapter(private var listener: OnItemClickListener<WordInfo>?)
    : RecyclerView.Adapter<WordInfoAdapter.WordInfoHolder>() {

    inner class WordInfoHolder(val binding: RecentSearchItemBinding)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordInfoHolder {
        return WordInfoHolder(
            RecentSearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WordInfoHolder, position: Int) {
        holder.binding.apply {
            val wordInfo = differ.currentList[position]
            val meaning = wordInfo.meanings.getOrNull(0)
            textWordSearched.text = wordInfo.word
            textWordPOS.text = meaning?.partOfSpeech ?: "No part of speech found"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<WordInfo>) {
        differ.submitList(list)
    }

    fun onClear() {
        listener = null
    }
}