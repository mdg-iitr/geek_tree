package com.codaira.geektree


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.codaira.geektree.model.Interests
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //for toolbar
        setSupportActionBar(toolbar)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

           var intRef=FirebaseDatabase.getInstance().reference.child("User").child(FirebaseAuth.getInstance().currentUser?.uid.toString()).child("interests")
        val firebaseAuth = FirebaseAuth.getInstance()

        //changing fragments when firebase auth changed
        firebaseAuth.addAuthStateListener {
            if (firebaseAuth.currentUser == null) {
                navController.navigate(R.id.action_destination_home_to_destination_login)
                bottom_nav.visibility = View.INVISIBLE
            } else {
                if (intRef.equals("")){
                    navController.navigate(R.id.destination_interests)
                }
                else {
                    navController.navigate(R.id.destination_home)
                    bottom_nav.visibility = View.VISIBLE
                    bottom_nav?.let {
                        NavigationUI.setupWithNavController(it, navController)
                    }
                    Interests.userInterests= arrayListOf(intRef.toString())
                }

            }
        }


        //setting title according to fragment
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            toolbar.title = navController.currentDestination?.label
        }

    }


}


