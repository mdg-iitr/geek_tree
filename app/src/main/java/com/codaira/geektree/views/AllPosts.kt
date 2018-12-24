package com.codaira.geektree.views

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.adapters.AllPostAdapter
import com.codaira.geektree.adapters.ProfileInterestAdapter
import com.codaira.geektree.models.Posts
import com.codaira.geektree.viewHolders.AllPostViewHolder
import com.codaira.geektree.viewHolders.HomePostsViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_all_posts.*
import kotlinx.android.synthetic.main.fragment_destination_profile.*
import kotlinx.android.synthetic.main.post_layout.view.*
import kotlinx.android.synthetic.main.post_of_interest.*


class AllPosts : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_posts, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_all_posts?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recycler_all_posts?.adapter = AllPostAdapter(com.codaira.geektree.models.Interests.allInterestsArray)



    }
}

