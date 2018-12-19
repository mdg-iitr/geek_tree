package com.codaira.geektree.model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R

class AllInterestsRecyclerAdapter(var interestslist: ArrayList<String>) :
    RecyclerView.Adapter<AllInterestsRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllInterestsRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.interests_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return interestslist.size
    }

    override fun onBindViewHolder(holder: AllInterestsRecyclerAdapter.ViewHolder, position: Int) {
        val interest: String = interestslist[position]
        holder.setInterestString(interest)

    }

    class ViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {
        val interest: String = Interests.allInterestsArray[adapterPosition]
        var list= arrayListOf<String>()
        lateinit var a: CheckBox

        fun setInterestString(interest: String) {
            a = itemview.findViewById<CheckBox>(R.id.interest_of_user)
            a.setText(interest)
        }

        init {
            itemview.setOnClickListener { a.isChecked = !a.isChecked }
            a.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    addItemToList()
                } else {
                     removeItemFromList()
                }
            }
        }

        fun removeItemFromList() {
         list.remove(interest)
        }
        fun addItemToList() {
            list.add(interest)
        }

    }

}