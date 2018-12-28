package com.codaira.geektree.views


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.codaira.geektree.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_email_verification.*


class EmailVerification : Fragment() {

    lateinit var firebaseAuth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        firebaseAuth = FirebaseAuth.getInstance()


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_verification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //btn to verify email
        verifyEmail_VerifyEmailFragment.setOnClickListener {
            firebaseAuth.currentUser?.sendEmailVerification()?.addOnSuccessListener {
                Toast.makeText(activity, "Verification Email Sent", Toast.LENGTH_SHORT).show()
                openEmailApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        //you need to run this user task to get updated current user status
        val userTask = firebaseAuth.currentUser?.reload()
        userTask?.addOnSuccessListener {
            if(firebaseAuth.currentUser?.isEmailVerified!!){
                Navigation.findNavController(activity as Activity, R.id.nav_host_fragment).navigate(
                    R.id.destination_home
                )
                var act=activity as MainActivity
                act.showBootomNav()
            }
        }
    }

    private fun openEmailApp() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
        activity!!.startActivity(intent)
    }
}
