package com.codaira.geektree.model

data class Interests(var interests: ArrayList<String>? = null){
    companion object {
        var allInterestsArray = arrayListOf<String>(
            "Photography",
            "Android Development",
            "Web development",
            "Designing",
            "Machine Learning",
            "Virtual Reality"
        )
    }
}