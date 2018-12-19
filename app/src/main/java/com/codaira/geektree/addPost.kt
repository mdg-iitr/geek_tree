
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
import com.codaira.geektree.model.Interests
import com.codaira.geektree.Adapters.PostInterestAdapter
import com.codaira.geektree.model.Posts
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

        //To show interests using recycler
        addpost_recycler?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        addpost_recycler?.adapter = PostInterestAdapter(Interests.userInterests)



        button_postimage_post.setOnClickListener {
            openGallery() // opens gallery to choose image from
        }
        button_addpost_post.setOnClickListener {
            addpost() // processes adding image and text post to firebase
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
        FirebaseDatabase.getInstance().reference.child("posts").child(postid).child("interests").setValue(Posts.postInterest)
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
