package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class VerifyRequestModel(
    @SerializedName("token")
    val customerToken: String,

    @SerializedName("email")
    val customerEmail: String
)