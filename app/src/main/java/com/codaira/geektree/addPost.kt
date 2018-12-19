
package com.codaira.geektree


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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.model.Posts
import com.codaira.geektree.model.user_interests
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_destination_add_post.*
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */

class destination_addPost : Fragment() {
    var url:String?=""
    var imageuri: Uri?=null
    var GalleryPick=1
    var postid=FirebaseAuth.getInstance().currentUser?.uid.toString()+ SimpleDateFormat("HH:mm").format(Calendar.getInstance().time) + SimpleDateFormat("dd: MM : yyyy").format(Calendar.getInstance().time)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_destination_add_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<RecyclerView>(R.id.addpost_recycler).layoutManager =
                LinearLayoutManager(activity, RecyclerView.VERTICAL, false) // adds recycler in vertical orientation
        val query = FirebaseDatabase.getInstance().reference.child("User").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("interests")
        val options =
            FirebaseRecyclerOptions.Builder<user_interests>().setQuery(query, user_interests::class.java).setLifecycleOwner(activity)
                .build()
        val adapter = object : FirebaseRecyclerAdapter<user_interests, InterestHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestHolder {
                return  InterestHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.interests_layout,
                        parent,
                        false
                    )
                )
            }

            override fun onBindViewHolder(p0:InterestHolder, p1: Int, p2: user_interests) { //binds data to objects
                p0.bind(p2)
            }
        }
        view.findViewById<RecyclerView>(R.id.addpost_recycler).adapter = adapter

        button_postimage_post.setOnClickListener {
            openGallery() // opens gallery to choose image from
        }
        button_addpost_post.setOnClickListener {
            addpost() // processes adding image and text post to firebase
        }

    }
    class InterestHolder(val customView: View, var interets:user_interests? = null) : RecyclerView.ViewHolder(customView) {
        fun bind(interets: user_interests?) {
            //customView.interest_of_user?.text=interets!!.list[]
        }
    }

    private fun addpost() {
        if (imageuri != null && !isEmpty(edit_posttext_post.text.toString())) {
            storeImageToFirebase() //stores image to storage and then updates on database
        }
        if (isEmpty(edit_posttext_post.text.toString())) {
            Toast.makeText(activity, "Please Enter Text", Toast.LENGTH_LONG).show()
        }
        else if( imageuri==null) {
            addPostToFirebase() //if no image added, updates text post to database
        }
    }

    private fun addPostToFirebase() {
        var post = Posts(
            edit_posttext_post.text.toString(),
            SimpleDateFormat("dd: MM : yyyy").format(Calendar.getInstance().time),
            SimpleDateFormat("HH:mm").format(Calendar.getInstance().time),
            FirebaseAuth.getInstance().currentUser?.email, url)
        FirebaseDatabase.getInstance().reference.child("posts").child(postid).setValue(post).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(activity, "Post added successfully", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(activity, "Post NOT added", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun storeImageToFirebase() {
        var uploadTask=FirebaseStorage.getInstance().reference.child("postImages").child(postid).putFile(
            imageuri!!)
        uploadTask.addOnSuccessListener {
            FirebaseStorage.getInstance().reference.child("postImages").child(postid).downloadUrl.addOnSuccessListener {//to get download url of image
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
        val gallery=Intent()
        gallery.setAction(Intent.ACTION_GET_CONTENT)
        gallery.setType("image/*")
        startActivityForResult(gallery, GalleryPick )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode== GalleryPick && resultCode==RESULT_OK && data!=null){
            imageuri=data.data
            button_postimage_post.setImageURI(imageuri)
        }
        else
            openGallery()
    }
}
