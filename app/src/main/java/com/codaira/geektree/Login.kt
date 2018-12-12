package com.codaira.geektree

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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*



private val TAG = "LoginActivity"
//global variables
private var email: String? = null
private var password: String? = null
//UI elements
private var etEmail: EditText? = null
private var etPassword: EditText? = null
private var btnLogin: Button? = null
private var btnSignUp: Button? = null
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
        button_signup.setOnClickListener {
            Navigation.findNavController(it).navigate(action_login_to_signUp)
        }
        button_login.setOnClickListener {
            loginUser(it)
        }

        initialise()
    }
    private fun initialise() {
        etEmail = view?.findViewById(R.id.text_email) as EditText
        etPassword = view?.findViewById(R.id.text_password) as EditText
        btnLogin = view?.findViewById(R.id.button_login) as Button
        btnSignUp = view?.findViewById(R.id.button_signup) as Button
        mAuth = FirebaseAuth.getInstance()


    }

    private fun loginUser(NavVar:View) {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            Navigation.findNavController(NavVar).navigate(action_login_to_home)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure")
                        Toast.makeText(activity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }



}




