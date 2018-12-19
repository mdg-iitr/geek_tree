package com.codaira.geektree


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

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
                bottom_nav.visibility=View.INVISIBLE
            }
            else{
                //checking if current user has his email verified
                navController.navigate(R.id.destination_interests)
                bottom_nav.visibility=View.VISIBLE
                bottom_nav?.let {
                    NavigationUI.setupWithNavController(it, navController)
                }

            }
        }


        //setting title according to fragment
        navController.addOnDestinationChangedListener{ controller, destination, arguments ->
            toolbar.title = navController.currentDestination?.label
        }

    }


}


