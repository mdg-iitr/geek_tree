package com.codaira.geektree.viewModels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codaira.geektree.data.FirebaseLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class UsersWebDviewModel : ViewModel() {
    val databaseref = FirebaseDatabase.getInstance().reference.child("interests").child("Android Development")

    val liveData = FirebaseLiveData(databaseref as Query)

    private val ListLiveData = Transformations.map(liveData, Deserializer())

    private class Deserializer: Function<DataSnapshot, MutableList<String>> {
        override fun apply(dataSnapshot: DataSnapshot): MutableList<String>? {
            return dataSnapshot.toUserList()
        }
    }

    fun getUserList(): LiveData<MutableList<String>> {
        return ListLiveData
    }
}

private fun DataSnapshot.toUserList(): MutableList<String>? {
    val list = mutableListOf<String>()
    for (snapshot in this.children) {
        list.add(snapshot.value.toString())
    }
    return list
}