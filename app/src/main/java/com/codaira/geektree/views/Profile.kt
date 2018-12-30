package com.codaira.geektree.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.codaira.geektree.databinding.FragmentDestinationProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.widget.TabHost
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.R
import com.codaira.geektree.adapters.ProfileInterestAdapter
import com.codaira.geektree.data.Interests
import com.codaira.geektree.data.User
import com.codaira.geektree.viewModels.InterestsUserViewModel
import com.codaira.geektree.views.MainActivity.Companion.user
import kotlinx.android.synthetic.main.fragment_destination_profile.*


class Profile : Fragment() {
    lateinit var firebaseUser: String
    lateinit var databaseref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_destination_profile, container, false)
        val binding = DataBindingUtil.bind<FragmentDestinationProfileBinding>(view)
        binding?.user=MainActivity.user
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
        databaseref = FirebaseDatabase.getInstance().reference.child("User").child(firebaseUser)

        val profileVModel = ViewModelProviders.of(this).get(InterestsUserViewModel::class.java)

        val pLiveData : LiveData<Interests> = profileVModel.getUserData()

        button_edit_branch.setOnClickListener {
            user?.branch=text_branch_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"branch updated successfully", Toast.LENGTH_SHORT).show()
            }
        }


        button_edit_year.setOnClickListener {
            user?.year=text_year_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"year updated successfully", Toast.LENGTH_SHORT).show()

            }

        }

        button_edit_fb.setOnClickListener {
            user?.fb=text_fb_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"fb link updated successfully", Toast.LENGTH_SHORT).show()

            }

        }
        button_edit_linkedin.setOnClickListener {
            user?.linkedin=text_linkedin_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"linkedin updated successfully", Toast.LENGTH_SHORT).show()

            }

        }

        button_edit_num.setOnClickListener {
            user?.phoneNumber=text_number_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"number updated successfully", Toast.LENGTH_SHORT).show()

            }

        }







        val host = view.findViewById(R.id.tabhost) as TabHost
        host.setup()

        //Tab 1
        var spec: TabHost.TabSpec = host.newTabSpec("Details")
        spec.setContent(R.id.details)
        spec.setIndicator("Details")
        host.addTab(spec)

        //Tab 2
        spec = host.newTabSpec("Interests")
        spec.setContent(R.id.Interests)
        spec.setIndicator("Interests")
        host.addTab(spec)

        recycler_profile_interests?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        pLiveData.observe(this, Observer {
            var intlist= it.interests
            recycler_profile_interests?.adapter = ProfileInterestAdapter(intlist)
        })

        button_profile_change_interests.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.destination_interests)

        }

    }
}

