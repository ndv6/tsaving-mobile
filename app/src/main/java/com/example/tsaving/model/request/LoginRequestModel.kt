package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("cust_email")
    val cust_email: String,

    @SerializedName("cust_password")
    val cust_password: String
)