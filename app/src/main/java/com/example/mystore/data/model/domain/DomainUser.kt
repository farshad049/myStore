package com.example.mystore.data.model.domain

data class DomainUser(
    val id : Int ,
    val name : Name ,
    val email: String,
    val username : String ,
    val phoneNumber : String ,
    val address : Address ,
    val greetingMessage : String
)
