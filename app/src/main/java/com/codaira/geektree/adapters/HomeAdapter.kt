package com.codaira.geektree.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.data.Posts
import com.codaira.geektree.viewHolders.HomePostsViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.post_layout.view.*

class HomeAdapter (val list: MutableList<Posts>) : RecyclerView.Adapter<HomePostsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HomePostsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false)
        return HomePostsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomePostsViewHolder, position: Int) {
        val post = list.get(position)

        holder.bind(post)
        val img = holder.customView.post_image
        if (post.image!!.isEmpty()) {
            //do nothing
        } else {
            Picasso.with(holder.customView.context).load(post.image).into(img)//loads image into imageholder
        }
    }
}
