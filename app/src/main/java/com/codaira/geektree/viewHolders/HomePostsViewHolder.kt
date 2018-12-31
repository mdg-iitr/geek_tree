package com.codaira.geektree.viewHolders

//ViewHolder for posts shown on homeScreen

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.data.Posts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.post_layout.view.*
import com.like.LikeButton
import com.like.OnLikeListener


class HomePostsViewHolder(val customView: View, var posts: Posts? = null) : RecyclerView.ViewHolder(customView) {

    fun bind(posts: Posts) {
        var firebaseuser = FirebaseAuth.getInstance().currentUser?.uid.toString()
        var firebaseref = FirebaseDatabase.getInstance().reference.child("User").child(firebaseuser).child("likedPosts")
        var key=  firebaseref.push().key


        var listLikedPosts: MutableList<Posts> = mutableListOf()
        customView.post_date?.text = posts.date
        customView.post_time?.text = posts.time
        customView.post_description?.text = posts.posttext
        customView.post_user_name?.text = posts.usernname
        var interest: String = ""
        posts.postInterestlist.forEach {
            interest = interest + "|" + it
        }

        customView.post_int?.text = interest

        customView.heart_button.setOnLikeListener(object : OnLikeListener {

            override fun liked(likeButton: LikeButton) {
                posts.selected="true"
                FirebaseDatabase.getInstance().reference.child("posts").child(posts.taskkey).setValue(posts)
                firebaseref.child(key!!).setValue(posts)



            }

            override fun unLiked(likeButton: LikeButton) {
                posts.selected="false"
                FirebaseDatabase.getInstance().reference.child("posts").child(posts.taskkey).setValue(posts)
                firebaseref.child(key!!).removeValue()
            }
        })
    }


}




