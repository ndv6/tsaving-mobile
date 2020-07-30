package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(

    val cust_email: String,
    val cust_password: String
)