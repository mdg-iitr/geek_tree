package com.codaira.geektree.adapters

//adapter for addPost's interests recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.viewHolders.PostInterestViewHolder

class PostInterestAdapter(var postinterestslist: MutableList<String>) :
    RecyclerView.Adapter<PostInterestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostInterestViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interests_layout, parent, false)
        return PostInterestViewHolder(v)
    }

    override fun getItemCount(): Int {
        return postinterestslist.size
    }

    override fun onBindViewHolder(holder: PostInterestViewHolder, position: Int) {
        val interest: String = postinterestslist[position]
        holder.setInterestString(interest)
    }


}
