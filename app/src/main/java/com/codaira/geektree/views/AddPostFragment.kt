package com.codaira.geektree.views


import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.data.Posts
import com.codaira.geektree.R
import com.codaira.geektree.adapters.ProfileInterestAdapter
import com.codaira.geektree.data.Interests
import com.codaira.geektree.data.User
import com.codaira.geektree.viewModels.InterestsUserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_destination_add_post.*
import kotlinx.android.synthetic.main.fragment_destination_profile.*
import java.text.SimpleDateFormat
import java.util.*


class AddPostFragment : Fragment() {

    var url: String? = ""
    var imageuri: Uri? = null
    var GalleryPick = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_destination_add_post, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val profileVModel = ViewModelProviders.of(this).get(InterestsUserViewModel::class.java)
        val pLiveData : LiveData<Interests> = profileVModel.getUserData()

        //To show interests using recycler-so the interests of previous post are not over written

        button_addinterest_post.setOnClickListener {
            Posts.postInterest.removeAll(com.codaira.geektree.data.Interests.allInterestsArray)
            addpost_recycler?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//            addpost_recycler?.adapter = PostInterestAdapter(MainActivity.user?.interests?.interests!!)
            pLiveData.observe(this,androidx.lifecycle.Observer {
                var intlist= it.interests
                recycler_profile_interests?.adapter = ProfileInterestAdapter(intlist)
            })
            addpost_recycler.visibility = View.VISIBLE
        }


        // opens gallery to choose image from

        button_postimage_post.setOnClickListener {
            openGallery()
        }

        // processes adding image and text post to firebase

        button_addpost_post.setOnClickListener {
            addpost()
        }
    }


    private fun addpost() {

        if (imageuri != null && !isEmpty(edit_posttext_post.text.toString())) {
            storeImageToFirebase() //stores image to storage and then updates on database
        }

        if (isEmpty(edit_posttext_post.text.toString())) {
            Toast.makeText(activity, "Please Enter Text", Toast.LENGTH_LONG).show()
        } else if (imageuri == null) {
            addPostToFirebase() //if no image added, updates text post to database
        }

    }


    private fun addPostToFirebase() {
        var post = Posts(
            edit_posttext_post.text.toString(),
            SimpleDateFormat("dd: MM : yyyy").format(Calendar.getInstance().time),
            SimpleDateFormat("HH:mm").format(Calendar.getInstance().time),
            FirebaseAuth.getInstance().currentUser?.email, url,Posts.postInterest)


        FirebaseDatabase.getInstance().reference.child("posts").push().setValue(post).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(activity, "Post added successfully", Toast.LENGTH_LONG).show()
                addpost_recycler.visibility = View.GONE
            } else {
                Toast.makeText(activity, "Post NOT added", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun storeImageToFirebase() {
        var postid =
            FirebaseAuth.getInstance().currentUser?.uid.toString() + SimpleDateFormat("HH:mm").format(Calendar.getInstance().time) + SimpleDateFormat(
                "dd: MM : yyyy"
            ).format(Calendar.getInstance().time)
        var uploadTask = FirebaseStorage.getInstance().reference.child("postImages").child(postid).putFile(
            imageuri!!
        )


        uploadTask.addOnSuccessListener {
            FirebaseStorage.getInstance().reference.child("postImages").child(postid).downloadUrl.addOnSuccessListener {
                //to get download url of image
                url = it.toString()
                text_prompt_post.text = imageuri!!.lastPathSegment?.toString()
                Toast.makeText(activity, "Media added successfully", Toast.LENGTH_LONG).show()
                addPostToFirebase()
            }.addOnFailureListener {
                Toast.makeText(activity, "Media NOT added", Toast.LENGTH_LONG).show()

            }
        }
    }


    private fun openGallery() {
        val gallery = Intent()
        gallery.setAction(Intent.ACTION_GET_CONTENT)
        gallery.setType("image/*")
        startActivityForResult(gallery, GalleryPick)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            imageuri = data.data
            button_postimage_post.setImageURI(imageuri)
        } else
            openGallery()
    }
}
