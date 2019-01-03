package com.codaira.geektree.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.adapters.AllPostAdapter
import kotlinx.android.synthetic.main.fragment_all_posts.*


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
        recycler_all_posts?.adapter = AllPostAdapter(com.codaira.geektree.data.Interests.allInterestsArray)


    }
}

