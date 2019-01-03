package com.codaira.geektree.viewHolders

import android.view.View
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.data.Posts
import com.codaira.geektree.R

class PostInterestViewHolder(var itemview: View) : RecyclerView.ViewHolder(itemview) {
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
        Posts.postInterest.remove(s)
    }

    fun addItemToList(s: String) {
        Posts.postInterest.add(s)
    }

}
