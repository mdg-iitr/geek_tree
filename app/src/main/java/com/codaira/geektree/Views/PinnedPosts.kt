package com.codaira.geektree.Views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codaira.geektree.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_destination_pinned_posts.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [destination_pinnedPosts.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [destination_pinnedPosts.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class destination_pinnedPosts : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_destination_pinned_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
    }
}

