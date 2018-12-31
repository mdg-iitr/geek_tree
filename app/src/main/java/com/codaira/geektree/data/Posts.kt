package com.codaira.geektree.data

import com.like.LikeButton

//contains fields shown in posts on homeScreen
//model class for the posts shown
//companion object saves interests of posts


data class Posts(
    var posttext: String? = "",
    var date: String? = "",
    var time: String? = "",
    var usernname: String? = "",
    var image: String? = "",
    var postInterestlist: MutableList<String> = mutableListOf(),
    var dp:String?="",
    var selected: String="",
    var taskkey:String=""
) {
    companion object {
        var postInterest = arrayListOf<String>()
    }
}