package com.codaira.geektree.viewModels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codaira.geektree.data.FirebaseLiveData
import com.codaira.geektree.data.Posts
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class InterestViewModel(interest:String) : ViewModel() {
    val databaseref = FirebaseDatabase.getInstance().reference.child("posts")

    val liveData = FirebaseLiveData(databaseref as Query)

    private val adListLiveData = Transformations.map(liveData, Deserializer(interest))

    private class Deserializer(var interest: String): Function<DataSnapshot, MutableList<Posts>> {
        override fun apply(dataSnapshot: DataSnapshot): MutableList<Posts>? {
            return dataSnapshot.toPostList(interest)
        }
    }

    fun getPostList(): LiveData<MutableList<Posts>> {
        return adListLiveData
    }
}

private fun DataSnapshot.toPostList(interest: String): MutableList<Posts>? {
    val list = mutableListOf<Posts>()
    for (snapshot in this.children) {
        val post = snapshot.getValue(Posts::class.java)
        if((post!!.postInterestlist).contains(interest)) {
            list.add(post)

        }
    }
    return list
}