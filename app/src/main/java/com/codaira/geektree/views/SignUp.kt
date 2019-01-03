package com.codaira.geektree.views


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.codaira.geektree.data.User
import com.codaira.geektree.R
import com.codaira.geektree.interfaces.CallbackInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_sign_up.*


class SignUp : Fragment() {
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mAuth = FirebaseAuth.getInstance()

        return inflater.inflate(R.layout.fragment_sign_up, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)


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
            val builder = AlertDialog.Builder(activity)
            val progressBar: View = layoutInflater.inflate(R.layout.progress, null)
            builder.setView(progressBar)
            val dialog = builder.create()
            dialog.show()


            val branch: String = spinner_branch_signup.selectedItem.toString()
            val year: String = spinner_year_signup.selectedItem.toString()
            val name = edit_name_signup.text.toString()
            val email = edit_email_signup.text.toString()
            val password = edit_password_signup.text.toString()
            val fb = edit_fb_signup.text.toString()
            val linkedin = edit_linkedin_signup.text.toString()
            val username = edit_username_signup.text.toString()
            val phoneNumber = edit_number_signup.text.toString()


            //to see if username already exists
            val ref = FirebaseDatabase.getInstance().reference.child("User")

            usernameCheck(username, ref, object: CallbackInterface<Boolean> {
                override fun callback(data: Boolean) {

                    if (!data) {


                        if (!isEmpty(name) && !isEmpty(email) && !isEmpty(year) && !isEmpty(branch) && !isEmpty(username) && password.length >= 6) {
                            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    val user =
                                        User(email, password, username, name, phoneNumber, fb, linkedin, branch, year)
                                    FirebaseDatabase.getInstance().reference.child("User")
                                        .child(mAuth.currentUser?.uid.toString())
                                        .setValue(user)
                                    mAuth.signInWithEmailAndPassword(email, password)
                                } else {
                                    Toast.makeText(activity, "Couldn't SignUp please try again.", Toast.LENGTH_SHORT)
                                        .show()
                                }
                                dialog.dismiss()
                            }
                        } else {
                            Toast.makeText(
                                activity,
                                "Please enter all details and password must be 6 character long.",
                                Toast.LENGTH_SHORT
                            ).show()
                            dialog.dismiss()
                        }



                    }
                }
            })


        }
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu.findItem(R.id.profile)
        menuItem.isVisible = false
        val menuI=menu.findItem(R.id.logout)
        menuI.isVisible = false
    }



    override fun onResume() {
        super.onResume()

        //to get updated current user status
        val userTask = mAuth.currentUser?.reload()
        userTask?.addOnSuccessListener {
            if(mAuth.currentUser?.isEmailVerified!!){
                Navigation.findNavController(activity as Activity,R.id.nav_host_fragment).navigate(R.id.destination_interests)
            }
        }
    }

    private fun usernameCheck(username: String, ref: DatabaseReference, @NonNull callbackInterface: CallbackInterface<Boolean>){

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {
                for (a in p0.children) {
                    for (b in a.children) {
                        if (b.value == username) {
                            Toast.makeText(activity, "username already exists", Toast.LENGTH_SHORT).show()
                            callbackInterface.callback(true)
                            return
                        }
                    }
                }
                callbackInterface.callback(false)
            }
        })

    }

}
