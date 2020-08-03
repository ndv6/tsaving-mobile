package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class GetTokenRequest (
    @SerializedName("email")
    val customerEmail: String
)