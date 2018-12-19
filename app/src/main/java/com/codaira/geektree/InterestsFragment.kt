package com.codaira.geektree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.model.Interests
import com.codaira.geektree.model.AllInterestsRecyclerAdapter
import com.codaira.geektree.model.user_interests
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_interests.*
import kotlinx.android.synthetic.main.interests_layout.*

class InterestsFragment : Fragment() {
    lateinit var firebasedatabase: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_interests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebasedatabase = FirebaseDatabase.getInstance().reference.child("User")
            .child(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .child("interests")

        interest_recycler?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        interest_recycler?.adapter = AllInterestsRecyclerAdapter(Interests.allInterestsArray)



//        button_save_interests.setOnClickListener {
//            user_interests(list)
//            list.forEach {
//                val key = firebasedatabase.push().key
//                var save = firebasedatabase.child(key!!).setValue(it)
//                save.addOnCompleteListener {
//                    Toast.makeText(activity, "InterestsFragment have been saved", Toast.LENGTH_LONG).show()
//                }.addOnFailureListener {
//                    Toast.makeText(activity, "InterestsFragment have NOT been saved", Toast.LENGTH_LONG).show()
//
//                }
//            }
//        }
        button_save_interests.setOnClickListener {
            Toast.makeText(activity,"sfd",Toast.LENGTH_SHORT).show()

            FirebaseDatabase.getInstance().reference.child("KT").setValue(Interests.userInterests)
        }
    }
}
