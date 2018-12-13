package com.codaira.geektree.model

class User {

        lateinit var username:String
        lateinit var name: String
        lateinit var password: String
        lateinit var email: String
        lateinit var phoneNumber: String
        lateinit var fb: String
        lateinit var linkedin: String

    constructor() {}


    constructor(email:String,password:String,username:String,name:String,phoneNumber:String,fb:String,linkedin:String){
            this.email=email
            this.password=password
            this.username=username
            this.name=name
            this.phoneNumber=phoneNumber
            this.fb=fb
            this.linkedin=linkedin
        }
    }