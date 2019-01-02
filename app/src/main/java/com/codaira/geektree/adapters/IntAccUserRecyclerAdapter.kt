package com.codaira.geektree.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.viewHolders.IntAccUsersViewHolder

class IntAccUserRecyclerAdapter(var interestslist: MutableList<String>) :
    RecyclerView.Adapter<IntAccUsersViewHolder>() {
    companion object {
        var userCondition:String=""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntAccUsersViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interests_profile_layout, parent, false)
        return IntAccUsersViewHolder(v)
    }

    override fun getItemCount(): Int {
        return interestslist.size
    }

    override fun onBindViewHolder(holder: IntAccUsersViewHolder, position: Int) {
        val interest: String = interestslist[position]
        holder.setInterestString(interest)
    }
}
