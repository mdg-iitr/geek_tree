package com.codaira.geektree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.model.Interests
import com.codaira.geektree.Adapters.AllInterestsRecyclerAdapter
import com.codaira.geektree.model.InterestList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_interests.*

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
        //setting recycler
        interest_recycler?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        interest_recycler?.adapter = AllInterestsRecyclerAdapter(Interests.allInterestsArray)


        button_save_interests.setOnClickListener {
            val nav=it
            if (!Interests.userInterests.isEmpty()) {
                val list = InterestList(Interests.userInterests)

                val save = firebasedatabase.setValue(list)

                save.addOnCompleteListener {
                    Toast.makeText(activity, "Interests have been saved", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(nav).navigate(R.id.action_interests_to_homeScreen)

                }.addOnFailureListener {
                    Toast.makeText(activity, "Interests have NOT been saved", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(activity, "Please enter your interests", Toast.LENGTH_LONG).show()

            }
        }
    }
}
