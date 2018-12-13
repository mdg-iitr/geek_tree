package com.codaira.geektree

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigator
import com.codaira.geektree.R.id.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*



private val TAG = "LoginActivity"
//global variables
private var email: String? = null
private var password: String? = null

//Firebase references
private var mAuth: FirebaseAuth? = null

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

        mAuth = FirebaseAuth.getInstance()

        button_signup.setOnClickListener {
            Navigation.findNavController(it).navigate(action_login_to_signUp)
        }
        button_login.setOnClickListener {
            loginUser(it)
        }

    }


    private fun loginUser(NavVar:View) {
        email = text_email.toString()
        password = text_password.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth?.signInWithEmailAndPassword(email!!, password!!)!!.addOnCompleteListener(activity!!)  { task ->
                if (task.isSuccessful){
                    Navigation.findNavController(NavVar).navigate(action_login_to_home)
                    Toast.makeText(activity,"login successful",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity,"Error: Enter details again",Toast.LENGTH_SHORT).show()
                }
            }


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure")
                        Toast.makeText(activity, "LogIn failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }



}




