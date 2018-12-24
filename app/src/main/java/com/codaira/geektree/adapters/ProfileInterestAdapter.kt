package com.codaira.geektree.adapters

//adapter for addPost's interests recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.viewHolders.PostInterestViewHolder
import com.codaira.geektree.viewHolders.ProfileInterestViewHolder

class ProfileInterestAdapter(var interestslist: MutableList<String>) :
    RecyclerView.Adapter<ProfileInterestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileInterestViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interests_profile_layout, parent, false)
        return ProfileInterestViewHolder(v)
    }

    override fun getItemCount(): Int {
        return interestslist.size
    }

    override fun onBindViewHolder(holder: ProfileInterestViewHolder, position: Int) {
        val interest: String = interestslist[position]
        holder.setInterestString(interest)
    }


}
