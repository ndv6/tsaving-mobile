package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequestModel(
    @SerializedName("cust_name")
    val cust_name: String,

    @SerializedName("cust_address")
    val cust_address: String,

    @SerializedName("cust_phone")
    val cust_phone: String,

    @SerializedName("cust_email")
    val cust_email: String,

    @SerializedName("cust_password")
    val cust_password: String,

    @SerializedName("channel")
    val channel: String
)