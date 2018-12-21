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
import com.codaira.geektree.R
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
        firebaseUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
        databaseref = FirebaseDatabase.getInstance().reference.child("User").child(firebaseUser)

        binding?.user=MainActivity.user
        return view
    }


    //todo: make it editable, make logout


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
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
        //todo:To show interests using recycler


    }
}

