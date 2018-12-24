package com.codaira.geektree.viewHolders

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.R.id.destination_all_posts
import com.codaira.geektree.adapters.AllPostAdapter
import com.codaira.geektree.models.Posts

class AllPostViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {

        lateinit var a: TextView

        fun setInterestString(interest: String) {
            a.setText(interest)
        }

        init {
            a = itemview.findViewById<TextView>(R.id.text_profile_interest)
            itemview.setOnClickListener {
               AllPostAdapter.queryCondition= it.findViewById<TextView>(R.id.text_profile_interest).text.toString()
                Navigation.findNavController(it).navigate(R.id.destination_interestAccPosts)

            }
        }
    }
