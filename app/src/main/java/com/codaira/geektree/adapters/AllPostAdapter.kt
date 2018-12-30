package com.codaira.geektree.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.viewHolders.AllPostViewHolder
import com.codaira.geektree.viewHolders.PostInterestViewHolder

class AllPostAdapter(var interestslist: MutableList<String>) :
    RecyclerView.Adapter<AllPostViewHolder>() {

    companion object {
        var queryCondition:String=""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPostViewHolder{
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interests_profile_layout, parent, false)
        return AllPostViewHolder(v)
    }

    override fun getItemCount(): Int {
        return interestslist.size
    }

    override fun onBindViewHolder(holder: AllPostViewHolder, position: Int) {
        val interest: String = interestslist[position]
        holder.setInterestString(interest)
    }


}
