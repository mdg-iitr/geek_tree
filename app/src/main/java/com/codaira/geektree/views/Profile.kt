package com.codaira.geektree.views

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.codaira.geektree.databinding.FragmentDestinationProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.widget.TabHost
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codaira.geektree.R
import com.codaira.geektree.adapters.ProfileInterestAdapter
import com.codaira.geektree.data.Interests
import com.codaira.geektree.data.User
import com.codaira.geektree.viewModels.InterestsUserViewModel
import com.codaira.geektree.views.MainActivity.Companion.user
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_destination_add_post.*
import kotlinx.android.synthetic.main.fragment_destination_profile.*


class Profile : Fragment() {
    lateinit var firebaseUser: String
    lateinit var databaseref: DatabaseReference
    var GalleryPick = 1
    var imageuri:Uri?=null
    var resulturi:Uri?=null
    var url:String?=""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_destination_profile, container, false)
        val binding = DataBindingUtil.bind<FragmentDestinationProfileBinding>(view)
        binding?.user=MainActivity.user
        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
        databaseref = FirebaseDatabase.getInstance().reference.child("User").child(firebaseUser)
        val profileVModel = ViewModelProviders.of(this).get(InterestsUserViewModel::class.java)
        val pLiveData : LiveData<Interests> = profileVModel.getUserData()

        val builder = AlertDialog.Builder(activity)
        val progressBar: View = layoutInflater.inflate(R.layout.progress, null)
        builder.setView(progressBar)
        val dialog = builder.create()


       var storageref= FirebaseStorage.getInstance().reference.child("profilePictures").child(firebaseUser)
        if(!user?.dp?.isEmpty()!!){
            storageref.downloadUrl.addOnSuccessListener {
                var imgurl = it.toString()
                Picasso.with(context).load(imgurl).into(profile_img)
            }
        }



        button_edit_branch.setOnClickListener {
            dialog.show()
            user?.branch=text_branch_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"branch updated successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }


        button_edit_year.setOnClickListener {
            dialog.show()

            user?.year=text_year_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"year updated successfully", Toast.LENGTH_SHORT).show()
dialog.dismiss()
            }

        }

        button_edit_fb.setOnClickListener {
            dialog.show()
            user?.fb=text_fb_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"fb link updated successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()

            }

        }
        button_edit_linkedin.setOnClickListener {
            dialog.show()
            user?.linkedin=text_linkedin_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"linkedin updated successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()

            }

        }

        button_edit_num.setOnClickListener {
            dialog.show()
            user?.phoneNumber=text_number_profile.text.toString()
            databaseref.setValue(user).addOnSuccessListener {
                Toast.makeText(activity,"number updated successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }
        profile_img.setOnClickListener {
            opengallery()
        }

        button_dp.setOnClickListener {
            dialog.show()

            var uploadTask =
                FirebaseStorage.getInstance().reference.child("profilePictures").child(firebaseUser).putFile(
                    resulturi!!
                )


            uploadTask.addOnSuccessListener {
                FirebaseStorage.getInstance().reference.child("profilePictures").child(firebaseUser)
                    .downloadUrl.addOnSuccessListener {
                    //to get download url of image
                    url = it.toString()
                    Toast.makeText(activity, "Profile Picture added successfully", Toast.LENGTH_LONG).show()
                    databaseref.child("dp").setValue(url)
                    dialog.dismiss()
                }.addOnFailureListener {
                    Toast.makeText(activity, "Profile Picture NOT added", Toast.LENGTH_LONG).show()
                    dialog.dismiss()
                }
            }

        }

        val host = view.findViewById(R.id.tabhost) as TabHost
        host.setup()

        //Tab 1
        var spec: TabHost.TabSpec = host.newTabSpec("Details")
        spec.setContent(R.id.details)
        spec.setIndicator("Details")
        host.addTab(spec)

        //Tab 2
        spec = host.newTabSpec("Interests")
        spec.setContent(R.id.Interests)
        spec.setIndicator("Interests")
        host.addTab(spec)

        recycler_profile_interests?.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        pLiveData.observe(this, Observer {
            var intlist= it.interests
            recycler_profile_interests?.adapter = ProfileInterestAdapter(intlist)
        })

        button_profile_change_interests.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.destination_interests)

        }

    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val menuItem = menu.findItem(R.id.profile)
        menuItem.setVisible(false)
    }
    private fun opengallery() {

        val gallery = Intent()
        gallery.setAction(Intent.ACTION_GET_CONTENT)
        gallery.setType("image/*")
        startActivityForResult(gallery, GalleryPick)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GalleryPick && resultCode == Activity.RESULT_OK && data != null) {
            imageuri = data.data
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(context!!,this)
        }
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            var result: CropImage.ActivityResult=CropImage.getActivityResult(data)
            if (resultCode==RESULT_OK){
                resulturi=result.uri
                profile_img.setImageURI(resulturi)



            }
        }
        }

    }


