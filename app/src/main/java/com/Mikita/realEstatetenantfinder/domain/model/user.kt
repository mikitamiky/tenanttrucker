package com.Mikita.realEstatetenantfinder.domain.model

//data class user(
//    var uid: String="",
//    var email: String="",
//    var name: String = "",
//    var role: String = "", // "tenant" or "landlord"
//    var profileImageUrl: String = ""
//)

class User{
    var email:String=""
    var pass:String=""
    var uid: String=""
    var role: String = ""
    var profileImageUrl: String = ""
    var name: String = ""




    constructor(email:String,pass:String,uid:String,role:String,profileImageUrl:String,name:String,){
        this.email=email
        this.pass=pass
        this.uid=uid
        this.role=role
        this.profileImageUrl=profileImageUrl
        this.name=name


    }
    constructor()
}