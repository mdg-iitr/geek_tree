package com.codaira.geektree.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import kotlinx.android.synthetic.main.interests_layout.view.*

class all_interests_adapter(var interestslist: List<String>):RecyclerView.Adapter<all_interests_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): all_interests_adapter.ViewHolder {
    val v= LayoutInflater.from(parent?.context).inflate(R.layout.interests_layout,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
    return interestslist.size
    }

    override fun onBindViewHolder(holder: all_interests_adapter.ViewHolder, position: Int) {
      val interest:String=interestslist[position]
        holder.item=interest
    }
    class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
     var item= itemview.interest_of_user.text.toString()
    }
}