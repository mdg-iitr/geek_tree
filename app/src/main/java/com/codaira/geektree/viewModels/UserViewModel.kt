package com.codaira.geektree.viewModels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codaira.geektree.data.FirebaseLiveData
import com.codaira.geektree.data.UserName
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class UserViewModel(dbref:DatabaseReference) : ViewModel() {

    val liveData = FirebaseLiveData(dbref as Query)

    private val ListLiveData = Transformations.map(liveData, Deserializer())

    private class Deserializer: Function<DataSnapshot, MutableList<UserName?>> {
        override fun apply(dataSnapshot: DataSnapshot): MutableList<UserName?> {
            return dataSnapshot.toUserList()
        }
    }

    fun getUserList(): LiveData<MutableList<UserName?>> {
        return ListLiveData
    }
}

private fun DataSnapshot.toUserList(): MutableList<UserName?> {
    val list = mutableListOf<UserName?>()
    for (snapshot in this.children) {
        val info=snapshot.getValue(UserName::class.java)
        list.add(info)
    }
    return list
}