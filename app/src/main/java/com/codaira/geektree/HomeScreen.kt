package com.codaira.geektree

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.model.Posts
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.post_layout.view.*

class HomeScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.homescreen_recycler).layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false) // adds recycler in vertical orientation
        val query = FirebaseDatabase.getInstance().reference.child("posts")
        val options =
            FirebaseRecyclerOptions.Builder<Posts>().setQuery(query, Posts::class.java).setLifecycleOwner(activity)
                .build()
        val adapter = object : FirebaseRecyclerAdapter<Posts, PostsHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsHolder {
                return PostsHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_layout, parent, false))
            }

            override fun onBindViewHolder(p0: PostsHolder, p1: Int, p2: Posts) { //binds data to objects
                p0.bind(p2)
                val img = p0.customView.post_image
                if (p2.image!!.isEmpty()) {
                    //do nothing
                } else {
                    Picasso.with(p0.customView.context).load(p2.image).into(img)//loads image into imageholder
                }
            }
        }
        view.findViewById<RecyclerView>(R.id.homescreen_recycler).adapter = adapter
    }

    class PostsHolder(val customView: View, var posts: Posts? = null) : RecyclerView.ViewHolder(customView) {
        fun bind(posts: Posts) {
            customView.post_date?.text = posts.date
            customView.post_time?.text = posts.time
            customView.post_description?.text = posts.posttext
            customView.post_user_name?.text = posts.userid

        }
    }
}
