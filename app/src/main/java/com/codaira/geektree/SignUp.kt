package com.codaira.geektree


import android.content.Context
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.codaira.geektree.model.User
import com.google.firebase.auth.FirebaseAuth
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
            val password = edit_password_signup.text.toString()
            val fb = edit_fb_signup.text.toString()
            val linkedin = edit_linkedin_signup.text.toString()
            val username = edit_username_signup.text.toString()
            val phoneNumber = edit_number_signup.text.toString()


            val mAuth = FirebaseAuth.getInstance()

            if (!isEmpty(name) && !isEmpty(email) && !isEmpty(year) && !isEmpty(branch) && !isEmpty(username) &&password.length>=6){
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val user = User(email,password,username,name,phoneNumber,fb,linkedin)
                        FirebaseDatabase.getInstance().reference.child("User").child(mAuth.currentUser?.uid.toString()).setValue(user)
                        mAuth.signInWithEmailAndPassword(email, password) //Note : Navigation will automatically be handled by main activity
                    }
                    else{
                        Toast.makeText(activity,"Couldn't login please try again.",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                Toast.makeText(activity,"Please enter all details and password must be 6 character long.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
