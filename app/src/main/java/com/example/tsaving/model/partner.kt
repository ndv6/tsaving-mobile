package com.example.tsaving.model

import com.google.gson.annotations.SerializedName

class Partner (
    @SerializedName("partner_id")
    val ptId: Int,
    @SerializedName("client_id")
    val clId: Int,
    @SerializedName("secret")
    val secret: String)