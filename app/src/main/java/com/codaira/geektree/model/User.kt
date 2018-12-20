package com.codaira.geektree.model

class User(
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

