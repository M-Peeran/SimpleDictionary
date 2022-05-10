package com.peeranm.simpledictionary.feature_word_meaning.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.peeranm.simpledictionary.R
import com.peeranm.simpledictionary.databinding.WordChildItemBinding
import com.peeranm.simpledictionary.databinding.WordGroupItemBinding
import com.peeranm.simpledictionary.feature_word_meaning.model.Definition

class WordDetailsAdapter() : BaseExpandableListAdapter() {

    private var data: Map<String, List<Definition>> = emptyMap()
    private var headers: List<String> = emptyList()

    fun submitData(data: Map<String, List<Definition>>) {
        this.data = data
        headers = data.keys.toList()
        notifyDataSetChanged()
    }

    override fun getGroupCount(): Int {
        return data.size
    }

    override fun getChildrenCount(listPosition: Int): Int {
        val headerKey = headers[listPosition]
        return data[headerKey]?.size ?: 0
    }

    override fun getGroup(listPosition: Int): String {
        return headers[listPosition]
    }

    override fun getChild(listPosition: Int, expandedListPosition: Int): Definition {
        val headerKey = headers[listPosition]
        return data[headerKey]?.get(expandedListPosition)
            ?: Definition("Corrupted data", "Corrupted data")
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        listPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val header = getGroup(listPosition)
        var view = convertView
        var binding: WordGroupItemBinding
        if (view == null) {
            binding = WordGroupItemBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
            view = binding.root
            view.setTag(R.id.GROUP_BINDING, binding)
        }
        binding = view.getTag(R.id.GROUP_BINDING) as WordGroupItemBinding
        binding.textGroup.text = header
        if (isExpanded) binding.arrowIndicator.setImageResource(R.drawable.ic_expand_less)
        else binding.arrowIndicator.setImageResource(R.drawable.ic_expand_more)
        return view
    }

    override fun getChildView(
        listPosition: Int,
        expandedListPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val child = getChild(listPosition, expandedListPosition)
        var view = convertView
        var binding: WordChildItemBinding
        if (view == null) {
            binding = WordChildItemBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
            view = binding.root
            view.setTag(R.id.CHILD_BINDING, binding)
        }
        binding = view.getTag(R.id.CHILD_BINDING) as WordChildItemBinding
        binding.textDefinition.text = child.definition
        binding.textExample.text = child.example
        return view
    }

    override fun isChildSelectable(
        listPosition: Int,
        expandedListPosition: Int
    ): Boolean { return true }
}