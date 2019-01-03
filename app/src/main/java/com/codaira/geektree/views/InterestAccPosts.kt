package com.codaira.geektree.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TabHost
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.adapters.AllPostAdapter
import com.codaira.geektree.adapters.HomeAdapter
import com.codaira.geektree.adapters.IntAccUserRecyclerAdapter
import com.codaira.geektree.data.Posts
import com.codaira.geektree.viewModels.*
import com.google.firebase.database.FirebaseDatabase
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
        recycler_users_interest?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val host = view.findViewById(R.id.tabhost_interest) as TabHost
        host.setup()

        //Tab 1
        var spec: TabHost.TabSpec = host.newTabSpec("Posts")
        spec.setContent(R.id.posts)
        spec.setIndicator("Posts")
        host.addTab(spec)

        //Tab 2
        spec = host.newTabSpec("Users")
        spec.setContent(R.id.users)
        spec.setIndicator("Users")
        host.addTab(spec)

        when (AllPostAdapter.queryCondition) {

            "Photography" -> {
                val a:InterestViewModel= InterestViewModel("Photography")
                val postLiveData: LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
                val viewmodel : UserViewModel = UserViewModel(FirebaseDatabase.getInstance().reference.child("interests").child("Photography"))
                val livedata = viewmodel.getUserList()
                livedata.observe(this, Observer {
                    recycler_users_interest.adapter = IntAccUserRecyclerAdapter(it)
                })
            }
            "Android Development" -> {
                val a:InterestViewModel=InterestViewModel("Android Development")
                val postLiveData: LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
                val viewmodel : UserViewModel = UserViewModel(FirebaseDatabase.getInstance().reference.child("interests").child("Android Development"))
                val livedata = viewmodel.getUserList()
                livedata.observe(this, Observer {
                    recycler_users_interest.adapter = IntAccUserRecyclerAdapter(it)
                })
            }
            "Web development" -> {
                val a:InterestViewModel=InterestViewModel("Web development")
                val postLiveData: LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
                val viewmodel : UserViewModel = UserViewModel(FirebaseDatabase.getInstance().reference.child("interests").child("Web Development"))
                val livedata = viewmodel.getUserList()
                livedata.observe(this, Observer {
                    recycler_users_interest.adapter = IntAccUserRecyclerAdapter(it)
                })
            }
            "Designing" -> {
                val a:InterestViewModel=InterestViewModel("Designing")
                val postLiveData: LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
                val viewmodel : UserViewModel = UserViewModel(FirebaseDatabase.getInstance().reference.child("interests").child("Designing"))
                val livedata = viewmodel.getUserList()
                livedata.observe(this, Observer {
                    recycler_users_interest.adapter = IntAccUserRecyclerAdapter(it)
                })
            }
            "Machine Learning" -> {
                val a:InterestViewModel=InterestViewModel("Machine Learning")
                val postLiveData: LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
                val viewmodel : UserViewModel = UserViewModel(FirebaseDatabase.getInstance().reference.child("interests").child("Machine Learning"))
                val livedata = viewmodel.getUserList()
                livedata.observe(this, Observer {
                    recycler_users_interest.adapter = IntAccUserRecyclerAdapter(it)
                })
            }
            "Virtual Reality" -> {
                val a:InterestViewModel=InterestViewModel("Virtual Reality")
                val postLiveData: LiveData<MutableList<Posts>> = a.getPostList()
                postLiveData.observe(this, Observer {
                    recycler_intaccposts.adapter = HomeAdapter(it)
                })
                val viewmodel : UserViewModel = UserViewModel(FirebaseDatabase.getInstance().reference.child("interests").child("Virtual Reality"))
                val livedata = viewmodel.getUserList()
                livedata.observe(this, Observer {
                    recycler_users_interest.adapter = IntAccUserRecyclerAdapter(it)
                })
            }
        }
    }
}