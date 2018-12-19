package com.codaira.geektree.model

data class Interests(var userInterests:String? = null){
    companion object{
        var allInterestsArray= listOf<String>("Photography", "Android Development","Web development","Designing","Machine Learning","Virtual Reality")
    }
}