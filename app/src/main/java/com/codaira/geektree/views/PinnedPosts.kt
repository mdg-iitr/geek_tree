package com.codaira.geektree.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.adapters.HomeAdapter
import com.codaira.geektree.data.Posts
import com.codaira.geektree.viewModels.PinnedPostsViewModel
import kotlinx.android.synthetic.main.fragment_destination_pinned_posts.*


class destination_pinnedPosts : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_destination_pinned_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.recycler_pinned).layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false) // adds recycler in vertical orientation

        val pinnedVModel = ViewModelProviders.of(this).get(PinnedPostsViewModel::class.java)

        val postLiveData : LiveData<MutableList<Posts>> = pinnedVModel.getPostList()

        postLiveData.observe(this, Observer {
            recycler_pinned.adapter = HomeAdapter(it)
        })
    }
}


