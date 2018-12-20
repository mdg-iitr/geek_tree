package com.codaira.geektree.model

class Posts(
    var posttext: String? = "",
    var date: String? = "",
    var time: String? = "",
    var userid: String? = "",
    var image: String? = "",
var interest:ArrayList<String>
) {
    companion object {
        var postInterest = arrayListOf<String>()
    }
}