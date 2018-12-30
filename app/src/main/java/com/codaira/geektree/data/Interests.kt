package com.codaira.geektree.data

//contains two lists:interest->user interests list and allInterestsArray->list of all the interests
//class to retrieve lists in recyclerViews of addPost/interest fragment

data class Interests(var interests: MutableList<String> = mutableListOf()){
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