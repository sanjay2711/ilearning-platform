package com.example.ilearning.domain.models

data class LoginResponse(
    var id : Int,
    var name : String,
    var username : String,
    var email : String,
    var phone : String,
)