package com.codaira.geektree.viewHolders

import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.adapters.IntAccUserRecyclerAdapter
import com.codaira.geektree.data.UserName
import kotlinx.android.synthetic.main.interests_profile_layout.view.*

class IntAccUsersViewHolder(val customView: View, var userName:UserName?=null) : RecyclerView.ViewHolder(customView) {
lateinit var a:TextView
    fun bind(userName: UserName) {
        a = customView.findViewById<TextView>(R.id.text_profile_interest)
        a.text = userName.username

       customView.setOnClickListener {
           IntAccUserRecyclerAdapter.userCondition=userName.uid
           Navigation.findNavController(it).navigate(R.id.destination_profileOthers)
       }

    }
}
