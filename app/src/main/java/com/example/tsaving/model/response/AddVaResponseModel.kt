package com.example.tsaving.model.response

import com.example.tsaving.model.VirtualAccount
import com.google.gson.annotations.SerializedName

data class AddVaResponseModel (
    val status: String,
    val message: String,
    val data: VirtualAccount

)