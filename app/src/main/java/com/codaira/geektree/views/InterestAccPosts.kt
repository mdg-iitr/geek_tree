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
import com.codaira.geektree.adapters.AllPostAdapter
import com.codaira.geektree.adapters.HomeAdapter
import com.codaira.geektree.data.Posts
import com.codaira.geektree.viewModels.*
import kotlinx.android.synthetic.main.fragment_interest_acc_posts.*


class InterestAccPosts : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interest_acc_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_intaccposts?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        when(AllPostAdapter.queryCondition){

            "Photography" -> { var a = ViewModelProviders.of(this).get(PhotographyViewModel::class.java)
                val postLiveData : LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
            }
            "Android Development" -> { var a=ViewModelProviders.of(this).get(ADviewModel::class.java)
                val postLiveData : LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
            }
            "Web development" -> {var a=ViewModelProviders.of(this).get(WebDviewModel::class.java)
                val postLiveData : LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
            }
            "Designing" -> { var a=ViewModelProviders.of(this).get(DesigningviewModel::class.java)
                val postLiveData : LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
            }
            "Machine Learning" -> { var a=ViewModelProviders.of(this).get(MLviewModel::class.java)
                val postLiveData : LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
            }
            "Virtual Reality" -> {var a=ViewModelProviders.of(this).get(VRviewModel::class.java)
                val postLiveData : LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
            }
        }
    }
}