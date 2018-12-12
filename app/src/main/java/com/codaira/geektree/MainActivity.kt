package com.codaira.geektree


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.codaira.geektree.R.layout.fragment_home_screen
import com.codaira.geektree.R.layout.fragment_login
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navController= Navigation.findNavController(this,R.id.nav_host_fragment)
        setupBottomNavMenu(navController)
        setupActionBar(navController)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.addAuthStateListener {
            if(firebaseAuth.currentUser==null){
                Navigation.findNavController(this, R.id.navigation_main).navigate(R.id.destination_login)
            }
        }

    }
    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav?.let {
            NavigationUI.setupWithNavController(it,navController)
        }

    }
    private fun setupActionBar(navController: NavController) {
        setupActionBarWithNavController(this,navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate((R.menu.menu_toolbar), menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val navController = Navigation.findNavController(this, R.id.navigation_main)
        val navigated= NavigationUI.onNavDestinationSelected(item!!,navController)
        return navigated||super.onOptionsItemSelected(item)
    }
}
