package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class SendMailRequest(
    @SerializedName("email")
    val customerEmail: String,

    @SerializedName("token")
    val customerToken: String
)