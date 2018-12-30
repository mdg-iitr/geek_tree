package com.codaira.geektree.viewModels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.codaira.geektree.data.FirebaseLiveData
import com.codaira.geektree.data.Interests
import com.codaira.geektree.data.Posts
import com.codaira.geektree.data.User
import com.codaira.geektree.views.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class InterestsUserViewModel : ViewModel() {
    val firebaseUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val databaseref = FirebaseDatabase.getInstance().reference.child("User").child(firebaseUser).child("interests").child("interests")

    val liveData = FirebaseLiveData(databaseref as Query)

    private val intLiveData = Transformations.map(liveData, Deserializer())

    private class Deserializer: Function<DataSnapshot, Interests> {
        override fun apply(dataSnapshot: DataSnapshot): Interests? {
            return dataSnapshot.getValue(Interests::class.java)
        }
    }

    fun getUserData(): LiveData<Interests> {
        return intLiveData
    }
}