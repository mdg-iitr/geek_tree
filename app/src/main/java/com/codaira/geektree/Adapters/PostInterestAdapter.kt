package com.codaira.geektree.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.model.Posts

class PostInterestAdapter(var postinterestslist: MutableList<String>) :
    RecyclerView.Adapter<PostInterestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interests_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return postinterestslist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val interest: String = postinterestslist[position]
        holder.setInterestString(interest)
    }

    class ViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {
        lateinit var a: CheckBox

        fun setInterestString(interest: String) {
            a.setText(interest)
        }

        init {
            a = itemview.findViewById<CheckBox>(R.id.interest_of_user)

            itemview.setOnClickListener { a.isChecked = !a.isChecked }
            a.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    addItemToList(a.text.toString())
                } else {
                    removeItemFromList(a.text.toString())
                }
            }
        }

        fun removeItemFromList(s: String) {
            Posts.postInterest?.remove(s)
        }

        fun addItemToList(s: String) {
            Posts.postInterest?.add(s)
        }

    }
}
