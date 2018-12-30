package com.codaira.geektree.viewHolders

import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.adapters.AllInterestsRecyclerAdapter
import com.codaira.geektree.R

class AllInterestsRecyclerViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {
    lateinit var a: CheckBox

    fun setInterestString(interest: String) {
        a.setText(interest)
    }

    init {
        a = itemview.findViewById(R.id.interest_of_user)

        itemview.setOnClickListener { a.isChecked = !a.isChecked }
        a.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                addItemToList(a.text.toString())
            } else {
                removeItemFromList(a.text.toString())
            }
        }
    }

    fun removeItemFromList(s: String) {
        AllInterestsRecyclerAdapter.temporaryInterestList.remove(s)
    }

    fun addItemToList(s: String) {
        AllInterestsRecyclerAdapter.temporaryInterestList.add(s)
    }

}

