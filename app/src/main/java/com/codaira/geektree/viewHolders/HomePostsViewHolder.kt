package com.codaira.geektree.viewHolders

//ViewHolder for posts shown on homeScreen

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.data.Posts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.post_layout.view.*
import com.like.LikeButton
import com.like.OnLikeListener


class HomePostsViewHolder(val customView: View, var posts: Posts? = null) : RecyclerView.ViewHolder(customView) {

    fun bind(posts: Posts) {
        var firebaseuser = FirebaseAuth.getInstance().currentUser?.uid.toString()
        var firebaseref = FirebaseDatabase.getInstance().reference.child("User").child(firebaseuser).child("likedPosts")

        firebaseref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.hasChild(posts.taskkey)){
                    customView.heart_button.setLiked(true)
                }
                else{
                    customView.heart_button.setLiked(false)
                }

            }


        }

        )
            customView.heart_button.setLiked(true)


        customView.post_date?.text = posts.date
        customView.post_time?.text = posts.time
        customView.post_description?.text = posts.posttext
        customView.post_user_name?.text = posts.usernname
        var interest: String = ""
        posts.postInterestlist.forEach {
            interest = interest + "|" + it
        }

        customView.post_int?.text = interest

        customView.heart_button.setOnClickListener {
            if (it.heart_button.isLiked){

                it.heart_button.setLiked(false)
                firebaseref.child(posts.taskkey).removeValue()
            }
            else{

                it.heart_button.setLiked(true)
                firebaseref.child(posts.taskkey).setValue(posts)
            }
        }
    }
}




