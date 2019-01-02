package com.codaira.geektree.viewModels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codaira.geektree.adapters.IntAccUserRecyclerAdapter
import com.codaira.geektree.data.FirebaseLiveData
import com.codaira.geektree.data.Posts
import com.codaira.geektree.data.User
import com.codaira.geektree.views.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import androidx.annotation.NonNull


class IntAccUserViewModel : ViewModel() {
    var databaseref =
        FirebaseDatabase.getInstance().reference.child("User")

    val liveData = FirebaseLiveData(databaseref as Query)

    private val LiveData = Transformations.map(liveData, Deserializer())

    private class Deserializer : Function<DataSnapshot, User> {
        override fun apply(dataSnapshot: DataSnapshot): User? {
            return dataSnapshot.getinfo()
        }
    }

    fun getUser(): LiveData<User> {
        return LiveData
    }
}
private fun DataSnapshot.getinfo(): User? {
    for (a in this.children) {
        for (b in a.children)
            if(b.value==IntAccUserRecyclerAdapter.userCondition)
            {
                return this.getValue(User::class.java)
            }
    }
    return MainActivity.user
}
