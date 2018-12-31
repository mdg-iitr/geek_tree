package com.codaira.geektree.viewHolders

//ViewHolder for posts shown on homeScreen

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.codaira.geektree.data.Posts
import kotlinx.android.synthetic.main.post_layout.view.*
import com.like.LikeButton
import com.like.OnLikeListener



class HomePostsViewHolder(val customView: View, var posts: Posts?=null) : RecyclerView.ViewHolder(customView) {

    companion object {
        var pinnedPostsList:MutableList<Posts> = mutableListOf()
    }

    fun bind(posts: Posts) {
        customView.post_date?.text = posts.date
        customView.post_time?.text = posts.time
        customView.post_description?.text = posts.posttext
        customView.post_user_name?.text = posts.usernname
        var interest:String=""
        posts.postInterestlist.forEach {
            interest=interest + "|" + it
        }

        customView.post_int?.text=interest

            customView.heart_button.setOnLikeListener(object : OnLikeListener {
                override fun liked(likeButton: LikeButton) {
                    pinnedPostsList.add(posts)

                }

                override fun unLiked(likeButton: LikeButton) {
                    pinnedPostsList.remove(posts)

                }
            })
        }

    }




