package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class EditProfileRequestModel(
    @SerializedName("cust_name")
    val customerName: String,

    @SerializedName("cust_email")
    val customerEmail: String,

    @SerializedName("cust_phone")
    val customerPhone: String,

    @SerializedName("cust_address")
    val customerAddress: String,

    @SerializedName("channel")
    val channel: String
)