package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class AddVaRequestModel(
    @SerializedName("va_color")
    val VaColor: String,

    @SerializedName("va_label")
    val VaLabel: String
)
