package com.example.tsaving.model.response

data class LoginResponseModel(
    val status: String,
    val message: String,
    val data: DataLogin
)

data class DataLogin (
    val token: String,
    val cust_email : String,
    val cust_name : String
)