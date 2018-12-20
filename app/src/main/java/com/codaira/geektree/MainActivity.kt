package com.codaira.geektree


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.codaira.geektree.model.Interests
import com.codaira.geektree.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var user : User? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //for toolbar
        setSupportActionBar(toolbar)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val firebaseAuth = FirebaseAuth.getInstance()

        //changing fragments when firebase auth changed
        firebaseAuth.addAuthStateListener {
            if (firebaseAuth.currentUser == null) {
                navController.navigate(R.id.action_destination_home_to_destination_login)
                bottom_nav.visibility = View.INVISIBLE
            } else {
                val intRef = FirebaseDatabase.getInstance().reference.child("User")
                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString())

                intRef.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        MainActivity.user = p0.getValue(User::class.java)

                        if(user?.interests?.interests==null){
                            navController.navigate(R.id.destination_interests)
                        }else{
                            navController.navigate(R.id.destination_home)
                            bottom_nav.visibility = View.VISIBLE
                            bottom_nav?.let {
                                NavigationUI.setupWithNavController(it, navController)
                            }
                        }
                    }

                })

            }
        }


        //setting title according to fragment
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            toolbar.title = navController.currentDestination?.label
        }

    }


}


