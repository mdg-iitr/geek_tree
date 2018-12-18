package com.codaira.geektree

import android.app.SearchManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.codaira.geektree.databinding.FragmentDestinationProfileBinding
import com.codaira.geektree.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class destination_profile : Fragment() {
    lateinit var firebaseUser: String
    lateinit var databaseref:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_destination_profile, container, false)
        val binding = DataBindingUtil.bind<FragmentDestinationProfileBinding>(view)
        firebaseUser=FirebaseAuth.getInstance().currentUser?.uid.toString()
        databaseref=FirebaseDatabase.getInstance().reference.child("User").child(firebaseUser)
        databaseref.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity,"Data not found",Toast.LENGTH_LONG).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user:User=p0.getValue(User::class.java)!!
                binding?.user=user
            }
        })
        return view
    }
}

