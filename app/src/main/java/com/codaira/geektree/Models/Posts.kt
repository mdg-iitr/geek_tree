package com.codaira.geektree.Models

//contains fields shown in posts on homeScreen
//model class for the posts shown
//companion object saves interests of posts


class Posts(
    var posttext: String? = "",
    var date: String? = "",
    var time: String? = "",
    var userid: String? = "",
    var image: String? = "") {
    companion object {
        var postInterest = arrayListOf<String>()
    }
}