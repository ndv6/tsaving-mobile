package com.example.tsaving.model

import com.google.gson.annotations.SerializedName

class EmailToken (
    @SerializedName("et_id")
    val etId: Int,
    @SerializedName("token")
    val token: String,
    @SerializedName("email")
    val email: String)