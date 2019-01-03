package com.codaira.geektree.viewModels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codaira.geektree.data.FirebaseLiveData
import com.codaira.geektree.data.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query


class IntAccUserViewModel(dbref:DatabaseReference) : ViewModel() {

    val liveData = FirebaseLiveData(dbref as Query)

    private val LiveData = Transformations.map(liveData, Deserializer())

    private class Deserializer : Function<DataSnapshot, User> {
        override fun apply(dataSnapshot: DataSnapshot): User? {
            return dataSnapshot.getValue(User::class.java)
        }
    }

    fun getUser(): LiveData<User> {
        return LiveData
    }
}

