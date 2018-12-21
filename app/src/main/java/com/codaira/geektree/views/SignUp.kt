package com.codaira.geektree.views


import android.content.Context
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.codaira.geektree.models.User
import com.codaira.geektree.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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

        //setting up spinner_branch
        val spinnerBranchArray: Array<String> = arrayOf(
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
        val spinnerBranchArrayAdapter = ArrayAdapter<String>(
            activity as Context, android.R.layout.simple_spinner_item, spinnerBranchArray
        ) //selected item will look like a spinner set from XML
        spinnerBranchArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_branch_signup.setAdapter(spinnerBranchArrayAdapter)

        //setting up spinner_year
        val spinnerYearArray: Array<String> = arrayOf("First", "Second", "Third", "Fourth", "Fifth")
        val spinnerYearArrayAdapter = ArrayAdapter<String>(
            activity as Context, android.R.layout.simple_spinner_item, spinnerYearArray
        ) //selected item will look like a spinner set from XML
        spinnerBranchArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_year_signup.setAdapter(spinnerYearArrayAdapter)


        button_authenticate_signup.setOnClickListener { view ->

            val branch: String = spinner_branch_signup.selectedItem.toString()
            val year: String = spinner_year_signup.selectedItem.toString()
            val name = edit_name_signup.text.toString()
            val email = edit_email_signup.text.toString()
            var password = edit_password_signup.text.toString()
            val fb = edit_fb_signup.text.toString()
            val linkedin = edit_linkedin_signup.text.toString()
            val username = edit_username_signup.text.toString()
            val phoneNumber = edit_number_signup.text.toString()


            val mAuth = FirebaseAuth.getInstance()
            //to see if username already exists
            val ref = FirebaseDatabase.getInstance().reference.child("User")

            usernameCheck(password, username, ref)


//            ref.addChildEventListener(object : ChildEventListener{
//                override fun onCancelled(p0: DatabaseError) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun onChildRemoved(p0: DataSnapshot) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                }
//
//                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
//                    if (p1==username){
//                        Toast.makeText(activity,"username already exists",Toast.LENGTH_LONG).show()
//                    }
//                    else{

            if (!isEmpty(name) && !isEmpty(email) && !isEmpty(year) && !isEmpty(branch) && !isEmpty(username) && password.length >= 6) {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            activity,
                            "please verify your email from the link sent to your id",
                            Toast.LENGTH_LONG
                        ).show()

                        mAuth.currentUser?.sendEmailVerification()

                        val user =
                            User(email, password, username, name, phoneNumber, fb, linkedin, branch, year)
                        FirebaseDatabase.getInstance().reference.child("User")
                            .child(mAuth.currentUser?.uid.toString())
                            .setValue(user)
                        mAuth.signInWithEmailAndPassword(email, password)
                    } else {
                        Toast.makeText(activity, "Couldn't SignUp please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(
                    activity,
                    "Please enter all details and password must be 6 character long.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            //    }
            // })
        }
    }

    private fun usernameCheck(password: String, username:String,ref:DatabaseReference ): String {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (a in p0.children) {
                    for (b in a.children) {
                        if (b.value == username) {
                            Toast.makeText(activity, "username already exists", Toast.LENGTH_SHORT).show()
                            var password = "123"
                        }
                    }
                }
            }
        })
        return password
    }

}
