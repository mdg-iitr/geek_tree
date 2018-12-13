package com.codaira.geektree

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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

    val mAuth = FirebaseAuth.getInstance()
    private var mDatabase = FirebaseDatabase.getInstance().getReference("email")
    private var name: String? = null
    private var password: String? = null
    private var email: String? = null
    private var phoneNumber: String? = null
    private var username: String? = null
    private var fb: String? = null
    private var linkedin: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        var spinnerBranchArray: Array<String> = arrayOf(
            "Architecture And Planning",
            "Biotechnology",
            "Chemical Engineering",
            "Civil Engineering",
            "Electrical Engineering",
            "Electronics And Communication Engineering",
            "Computer science and Engineering",
            "Mechanical Engineering",
            "Production and Industrial Engineering",
            "Polymer Science and Engineering",
            "Metallurgical and Materials Engineering",
            "Engineering Physics",
            "Geological Technology",
            "Geophysical Technology",
            "Applied Mathematics",
            "Int Msc Physics",
            "Int Msc Chemistry"
        )
        val spinnerbranch = ArrayAdapter<String>(
            activity as Context, android.R.layout.simple_spinner_item, spinnerBranchArray
        ) //selected item will look like a spinner set from XML
        spinnerbranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_branch.setAdapter(spinnerbranch)
        var branch: String? = null
        spinner_branch.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                branch = parent?.getItemIdAtPosition(position).toString()

            }
        }

        var spinnerYearArray: Array<String> = arrayOf("First", "Second", "Third", "Fourth", "Fifth")
        val spinneryear = ArrayAdapter<String>(
            activity as Context, android.R.layout.simple_spinner_item, spinnerYearArray
        ) //selected item will look like a spinner set from XML
        spinneryear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_year.setAdapter(spinneryear)

        var year: String? = null
        spinner_year.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //do nothing
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                year = parent?.getItemIdAtPosition(position).toString()

            }
        }

        val builder = AlertDialog.Builder(activity)
        var pbar: View = layoutInflater.inflate(R.layout.progressbar, null)
        builder.setView(pbar)
        val dialog = builder.create()

        name = signup_name.toString()
        email = signup_id.toString()
        password = signup_password.toString()
        fb = signup_fb.toString()
        linkedin = signup_linkedin.toString()
        username = signup_username.toString()

        button_authenticate.setOnClickListener {
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(
                    year
                ) && !TextUtils.isEmpty(branch) && !TextUtils.isEmpty(fb) && !TextUtils.isEmpty(username)
            ) {
                dialog.show()
                mAuth!!.createUserWithEmailAndPassword(email!!, password!!).addOnCompleteListener(activity!!) { task ->
                    dialog.dismiss()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val mUser = mAuth!!.currentUser
                        mUser!!.sendEmailVerification()
                            .addOnCompleteListener(Activity()) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        activity,
                                        "Verification email sent to " + mUser.email!!,
                                        LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(activity, "Failed to send verification email.", LENGTH_SHORT).show()
                                }
                            }
                        Navigation.findNavController(fragment_sign_up as View).navigate(action_signUp_to_interests)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(activity, "Authentication failed.", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(activity, "Enter all details", Toast.LENGTH_SHORT).show()
            }


        }
    }
}

