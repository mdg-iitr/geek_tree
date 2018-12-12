package com.codaira.geektree

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.codaira.geektree.R.id.action_signUp_to_interests
import com.codaira.geektree.R.layout.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUp : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        val spinnerbranch:Spinner=view.findViewById(R.id.spinner_branch)
        spinnerbranch.adapter = ArrayAdapter(activity, R.layout.fragment_sign_up,resources.getStringArray(R.array.branches_array))as SpinnerAdapter?
        var branch:String?=null
        spinnerbranch.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                branch=parent?.getItemIdAtPosition(position).toString()

            }
        }

        val spinneryear:Spinner=view.findViewById(R.id.spinner_year)
        spinneryear.adapter = ArrayAdapter(activity, R.layout.fragment_sign_up,resources.getStringArray(R.array.year_array))as SpinnerAdapter?
        var year:String?=null
        spinneryear.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                year=parent?.getItemIdAtPosition(position).toString()

            }
        }
        var Name =view.findViewById<View>(R.id.signup_name) as EditText
        var Email=view.findViewById<View>(R.id.signup_id)as EditText
        var Password=view.findViewById<View>(R.id.signup_password)as EditText
        var phoneNumber=view.findViewById<View>(R.id.signup_number)as EditText
        var username=view.findViewById<View>(R.id.signup_username)as EditText
        var btnauth=view.findViewById<View>(R.id.button_signup)as Button
        var mDatabase:FirebaseDatabase= FirebaseDatabase.getInstance()
        var mDatabaseReference:DatabaseReference=mDatabase!!.reference!!.child("Users")
        var mAuth:FirebaseAuth= FirebaseAuth.getInstance()
        val builder = AlertDialog.Builder(activity)
        var pbar:View= layoutInflater.inflate(R.layout.progressbar,null)
        builder.setView(pbar)
        val dialog=builder.create()
        var TAG="SignUp"

        btnauth.setOnClickListener {
            var name=Name.text.toString()
            var id=Email.text.toString()
            var password=Password.text.toString()


            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(id) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(year) && !TextUtils.isEmpty(branch)) {
             dialog.show()
            } else {
                Toast.makeText( activity,"Enter all details", Toast.LENGTH_SHORT).show()
            }

            mAuth!!.createUserWithEmailAndPassword(id!!, password!!).addOnCompleteListener(activity!!) {task ->dialog.dismiss()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val userId = mAuth!!.currentUser!!.uid
                        //Verify Email

                            val mUser = mAuth!!.currentUser;
                            mUser!!.sendEmailVerification()
                                .addOnCompleteListener(Activity()) { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(activity,
                                            "Verification email sent to " + mUser.getEmail(),LENGTH_SHORT).show()
                                    } else {
                                        Log.e(TAG, "sendEmailVerification", task.exception)
                                        Toast.makeText(activity,
                                            "Failed to send verification email.",LENGTH_SHORT).show()
                                    }
                                }


                        //update user profile information
                        val currentUserDb = mDatabaseReference!!.child(userId)
                        currentUserDb.child("name").setValue(name)
                        Navigation.findNavController(it).navigate(action_signUp_to_interests)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_LONG).show()
                    }
                }

        }


    }

}
