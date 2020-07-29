package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class LoginRequestModel(
    @SerializedName("cust_email")
    val customerEmail: String,

    @SerializedName("cust_password")
    val customerPassword: String
)