package com.codaira.geektree.viewModels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codaira.geektree.data.FirebaseLiveData
import com.codaira.geektree.data.Posts
import com.codaira.geektree.views.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class PinnedPostsViewModel: ViewModel() {
    var firebaseuser = FirebaseAuth.getInstance().currentUser?.uid.toString()
    var firebaseref = FirebaseDatabase.getInstance().reference.child("User").child(firebaseuser).child("likedPosts")

    val liveData = FirebaseLiveData(firebaseref as Query)

    private val likedpostListLiveData = Transformations.map(liveData, Deserializer())

    private class Deserializer: Function<DataSnapshot, MutableList<Posts>> {
        override fun apply(dataSnapshot: DataSnapshot): MutableList<Posts>? {
            return dataSnapshot.toPostList()
        }
    }

    fun getPostList(): LiveData<MutableList<Posts>> {
        return likedpostListLiveData
    }
}

private fun DataSnapshot.toPostList(): MutableList<Posts>? {
    val list = mutableListOf<Posts>()
    for (snapshot in this.children) {
        val post = snapshot.getValue(Posts::class.java)
            list.add(post!!)
    }
    return list
}