package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class AddVaRequestModel(
    @SerializedName("va_color")
    val va_color: String,

    @SerializedName("va_label")
    val va_label: String
)
