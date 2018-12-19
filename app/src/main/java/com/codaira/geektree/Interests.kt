package com.codaira.geektree

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.codaira.geektree.model.interestsClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_interests.*

class Interests : Fragment() {
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
        var list = arrayListOf<String>()
        checkBox_interest1.setOnCheckedChangeListener { buttonView, isChecked ->
            list.add(checkBox_interest1.text.toString())
        }
        checkBox_interest2.setOnCheckedChangeListener { buttonView, isChecked ->
            list.add(checkBox_interest2.text.toString())
        }
        checkBox_interest3.setOnCheckedChangeListener { buttonView, isChecked ->
            list.add(checkBox_interest3.text.toString())
        }
        checkBox_interest4.setOnCheckedChangeListener { buttonView, isChecked ->
            list.add(checkBox_interest4.text.toString())
        }
        checkBox_interest5.setOnCheckedChangeListener { buttonView, isChecked ->
            list.add(checkBox_interest5.text.toString())
        }
        checkBox_interest6.setOnCheckedChangeListener { buttonView, isChecked ->
            list.add(checkBox_interest6.text.toString())
        }
        checkBox_interest7.setOnCheckedChangeListener { buttonView, isChecked ->
            list.add(checkBox_interest7.text.toString())
        }
        checkBox_interest8.setOnCheckedChangeListener { buttonView, isChecked ->
            list.add(checkBox_interest8.text.toString())
        }

            button_save_interests.setOnClickListener {
                interestsClass(list)
                list.forEach {
                    val key = firebasedatabase.push().key
                    var save = firebasedatabase.child(key!!).setValue(it)
                    save.addOnCompleteListener {
                        Toast.makeText(activity, "Interests have been saved", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(activity, "Interests have NOT been saved", Toast.LENGTH_LONG).show()

                    }
                }
            }

        }
    }
