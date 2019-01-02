package com.codaira.geektree.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.codaira.geektree.R
import com.codaira.geektree.adapters.IntAccUserRecyclerAdapter
import com.codaira.geektree.viewModels.IntAccUserViewModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile_others.*

class ProfileOthers : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_others, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewmodel : IntAccUserViewModel = IntAccUserViewModel(FirebaseDatabase.getInstance().reference.child("User").child(IntAccUserRecyclerAdapter.userCondition))

        val livedata = viewmodel.getUser()

        livedata.observe(this, Observer {a->
            text_name_p.text = a?.name
            text_branch_p.text = a?.branch
            text_year_p.text = a?.year
            text_email_p.text = a?.email
            text_number_p.text = a?.phoneNumber
            text_fb_p.text = a?.fb
            text_linkedin_p.text = a?.linkedin
            text_username_p.text = a?.username


            var storageref = FirebaseStorage.getInstance().reference.child("profilePictures").child(a.username!!)
            if (!a.dp.isEmpty()!!) {
                storageref.downloadUrl.addOnSuccessListener {
                    var imgurl = it.toString()
                    Picasso.with(context).load(imgurl).into(p_img)
                }
            }
        })

    }
}