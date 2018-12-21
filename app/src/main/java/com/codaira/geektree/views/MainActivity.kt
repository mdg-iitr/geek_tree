package com.codaira.geektree.views


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.codaira.geektree.models.User
import com.codaira.geektree.R
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
                navController.navigate(R.id.destination_login)
                bottom_nav.visibility = View.INVISIBLE
            } else {
                val intRef = FirebaseDatabase.getInstance().reference.child("User")
                    .child(FirebaseAuth.getInstance().currentUser?.uid.toString())

                intRef.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        user = p0.getValue(User::class.java)
                        if (!firebaseAuth!!.currentUser!!.isEmailVerified){
                            navController.navigate(R.id.destination_signup)
                            bottom_nav.visibility = View.INVISIBLE
                        }
                        else {

                            if (user?.interests?.interests == null) {
                                navController.navigate(R.id.destination_interests)
                                bottom_nav.visibility = View.INVISIBLE
                            } else {
                                navController.navigate(R.id.destination_home)
                                showBootomNav()
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



    public fun showBootomNav(){
        bottom_nav.visibility = View.VISIBLE
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it, Navigation.findNavController(this,
                R.id.nav_host_fragment
            ))
        }
    }
}


