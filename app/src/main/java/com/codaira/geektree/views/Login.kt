package com.codaira.geektree.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.codaira.geektree.R
import com.codaira.geektree.R.id.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class Login : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


        proceedToSignUpTextView_SignInFragment.setOnClickListener {
            Navigation.findNavController(it).navigate(action_login_to_signUp)
        }
        signInButton_SignInFragment.setOnClickListener {
            loginUser()
        }

    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu.findItem(R.id.profile)
        menuItem.isVisible = false
        val menuI=menu.findItem(R.id.logout)
        menuI.isVisible = false
    }


    private fun loginUser() {
        val builder = AlertDialog.Builder(activity)
        val progressBar: View = layoutInflater.inflate(R.layout.progress, null)
        builder.setView(progressBar)
        val dialog = builder.create()
        dialog.show()


        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            editEmail_signInFragment.text.toString(),
            editPassword_signInFragment.text.toString()
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(activity, "Successful SignIn", Toast.LENGTH_SHORT).show() //to home through main activity
            } else {
                textView_SignInFragment.text = "Enter Correct Details"
            }
            dialog.dismiss()
        }

    }

}




