package com.codaira.geektree.data

//fields of user saved in database
//firebase data binded to it in mainActivity

data class User(
    var email: String? = "",
    var password: String? = "",
    var username: String? = "",
    var name: String? = "",
    var phoneNumber: String? = "",
    var fb: String? = "",
    var linkedin: String? = "",
    var branch: String? = "",
    var year: String = "",
    var interests: Interests? = null
)

