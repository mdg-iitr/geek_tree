package com.codaira.geektree.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import kotlinx.android.synthetic.main.interests_layout.view.*

class All_Interests_Recycler_Adapter(var interestslist: ArrayList<String>) :
    RecyclerView.Adapter<All_Interests_Recycler_Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): All_Interests_Recycler_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interests_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return interestslist.size
    }

    override fun onBindViewHolder(holder: All_Interests_Recycler_Adapter.ViewHolder, position: Int) {
        val interest: String = interestslist[position]
        holder.item = interest
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var item = itemview.interest_of_user.text.toString()
    }
}