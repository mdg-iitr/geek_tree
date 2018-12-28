package com.codaira.geektree.viewModels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codaira.geektree.data.HomeLiveData
import com.codaira.geektree.data.Posts
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class WebDviewModel : ViewModel() {
    val databaseref = FirebaseDatabase.getInstance().reference.child("posts")

    val liveData = HomeLiveData(databaseref as Query)

    private val wdListLiveData = Transformations.map(liveData, Deserializer())

    private class Deserializer: Function<DataSnapshot, MutableList<Posts>> {
        override fun apply(dataSnapshot: DataSnapshot): MutableList<Posts>? {
            return dataSnapshot.toPostList()
        }
    }

    fun getPostList(): LiveData<MutableList<Posts>> {
        return wdListLiveData
    }
}

private fun DataSnapshot.toPostList(): MutableList<Posts>? {
    val list = mutableListOf<Posts>()
    for (snapshot in this.children) {
        val post = snapshot.getValue(Posts::class.java)
        if((post!!.postInterestlist).contains("Web development")) {
            list.add(post!!)

        }
    }
    return list
}