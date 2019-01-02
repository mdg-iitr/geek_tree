package com.codaira.geektree.viewHolders

import android.view.View
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.adapters.IntAccUserRecyclerAdapter
import kotlinx.android.synthetic.main.interests_profile_layout.view.*

class IntAccUsersViewHolder(val customView: View) : RecyclerView.ViewHolder(customView) {
lateinit var a:TextView
    fun setInterestString(interest: String) {
        a = customView.findViewById<TextView>(R.id.text_profile_interest)
        a.setText(interest)
    }

    init {
       customView.setOnClickListener {
           IntAccUserRecyclerAdapter.userCondition=it.text_profile_interest.text.toString()
           Navigation.findNavController(it).navigate(R.id.destination_profileOthers)
       }

    }
}
