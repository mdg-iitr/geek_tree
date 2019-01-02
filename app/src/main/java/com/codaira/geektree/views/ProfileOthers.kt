package com.codaira.geektree.views


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.codaira.geektree.R
import com.codaira.geektree.R.layout.fragment_all_posts
import com.codaira.geektree.adapters.HomeAdapter
import com.codaira.geektree.adapters.IntAccUserRecyclerAdapter
import com.codaira.geektree.data.Posts
import com.codaira.geektree.data.User
import com.codaira.geektree.viewModels.HomeViewModel
import com.codaira.geektree.viewModels.IntAccUserViewModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_destination_profile.*
import kotlinx.android.synthetic.main.fragment_home_screen.*
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
       FirebaseDatabase.getInstance().reference.child("User").child(IntAccUserRecyclerAdapter.userCondition).addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    var a = p0.getValue(User::class.java)
                    text_name_p.text = a?.name
                    text_branch_p.text = a?.branch
                    text_year_p.text = a?.year
                    text_email_p.text = a?.email
                    text_number_p.text = a?.phoneNumber
                    text_fb_p.text = a?.fb
                    text_linkedin_p.text = a?.linkedin
                    text_username_p.text = a?.username

                }


            })

    }


//
//        val intUserModel = ViewModelProviders.of(this).get(IntAccUserViewModel::class.java)
//        val liveData: LiveData<User> = intUserModel.getUser()
//
//        liveData.observe(this, Observer {
//            text_name_p.text=it.name
//            text_branch_p.text=it.branch
//            text_year_p.text=it.year
//            text_email_p.text=it.email
//            text_number_p.text=it.phoneNumber
//            text_fb_p.text=it.fb
//            text_linkedin_p.text=it.linkedin
//            text_username_p.text=it.username
//
//
//
//            var storageref = FirebaseStorage.getInstance().reference.child("profilePictures").child(it?.username!!)
//            if (it.dp.isEmpty()) {
//                storageref.downloadUrl.addOnSuccessListener {
//                    var imgurl = it.toString()
//                    Picasso.with(context).load(imgurl).into(p_img)
//                }
//            }
//
//        })

    //  }

}
