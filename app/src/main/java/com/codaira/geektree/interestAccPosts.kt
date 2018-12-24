package com.codaira.geektree


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.adapters.AllPostAdapter
import com.codaira.geektree.models.Posts
import com.codaira.geektree.viewHolders.HomePostsViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_interest_acc_posts.*
import kotlinx.android.synthetic.main.post_layout.view.*


class interestAccPosts : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interest_acc_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = FirebaseDatabase.getInstance().reference.child("Interests")
            .child(AllPostAdapter.queryCondition)
        val options =
            FirebaseRecyclerOptions.Builder<Posts>().setQuery(query, Posts::class.java).setLifecycleOwner(activity)
                .build()



        val adapter = object : FirebaseRecyclerAdapter<Posts, HomePostsViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePostsViewHolder {
                return HomePostsViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.post_layout,
                        parent,
                        false
                    )
                )
            }

            override fun onBindViewHolder(p0: HomePostsViewHolder, p1: Int, p2: Posts) { //binds data to objects
                p0.bind(p2)
                val img = p0.customView.post_image
                if (p2.image!!.isEmpty()) {
                    //do nothing
                } else {
                    Picasso.with(p0.customView.context).load(p2.image).into(img)//loads image into imageholder
                }
            }
        }

        recycler_intaccposts?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        recycler_intaccposts?.adapter = adapter

    }
}
