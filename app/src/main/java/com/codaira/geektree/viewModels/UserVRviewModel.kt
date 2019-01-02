package com.codaira.geektree.viewModels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codaira.geektree.data.FirebaseLiveData
import com.codaira.geektree.data.UserName
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class UsersVRviewModel : ViewModel() {
    val databaseref = FirebaseDatabase.getInstance().reference.child("interests").child("Android Development")

    val liveData = FirebaseLiveData(databaseref as Query)

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
        var info=snapshot.getValue(UserName::class.java)
        list.add(info)
    }
    return list
}