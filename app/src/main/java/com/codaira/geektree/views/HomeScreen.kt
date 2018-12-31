package com.codaira.geektree.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.data.Posts
import com.codaira.geektree.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codaira.geektree.adapters.HomeAdapter
import com.codaira.geektree.viewModels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home_screen.*
import java.util.*


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

        val homeVModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val postLiveData : LiveData<MutableList<Posts>> = homeVModel.getPostList()

        postLiveData.observe(this, Observer {
            homescreen_recycler.adapter = HomeAdapter(it)
        })
    }
}
