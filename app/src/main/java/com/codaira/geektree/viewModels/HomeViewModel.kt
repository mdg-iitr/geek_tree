package com.codaira.geektree.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codaira.geektree.data.FirebaseLiveData
import com.codaira.geektree.data.Posts
import androidx.lifecycle.Transformations
import com.codaira.geektree.views.MainActivity.Companion.user
import androidx.arch.core.util.Function
import com.google.firebase.database.*


class HomeViewModel : ViewModel() {
    val databaseref = FirebaseDatabase.getInstance().reference.child("posts")

    val liveData = FirebaseLiveData(databaseref as Query)

    private val postListLiveData = Transformations.map(liveData, Deserializer())

    private class Deserializer: Function <DataSnapshot, MutableList<Posts>> {
        override fun apply(dataSnapshot: DataSnapshot): MutableList<Posts>? {
            return dataSnapshot.toPostList()
        }
    }

    fun getPostList(): LiveData<MutableList<Posts>> {
        return postListLiveData
    }
}

private fun DataSnapshot.toPostList(): MutableList<Posts>? {
    val list = mutableListOf<Posts>()
    for (snapshot in this.children) {
        val post = snapshot.getValue(Posts::class.java)
            if(!(user?.interests?.interests!!.minus(post!!.postInterestlist)== user?.interests?.interests!!)) {
                list.add(post)

        }
    }
    return list
}