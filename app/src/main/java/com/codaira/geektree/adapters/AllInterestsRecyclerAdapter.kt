package com.codaira.geektree.adapters

//Adapter to save interests of user on database

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.viewHolders.AllInterestsRecyclerViewHolder

class AllInterestsRecyclerAdapter(var interestslist: ArrayList<String>) :
    RecyclerView.Adapter<AllInterestsRecyclerViewHolder>() {

    companion object {
        var temporaryInterestList = mutableListOf<String>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllInterestsRecyclerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interests_layout, parent, false)
        return AllInterestsRecyclerViewHolder(v)
    }

    override fun getItemCount(): Int {
        return interestslist.size
    }

    override fun onBindViewHolder(holder: AllInterestsRecyclerViewHolder, position: Int) {
        val interest: String = interestslist[position]
        holder.setInterestString(interest)
    }
}

