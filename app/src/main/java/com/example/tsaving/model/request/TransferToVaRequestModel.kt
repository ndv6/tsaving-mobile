package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class TransferToVaRequestModel(
    val va_num: String,
    val va_balance: Int
)