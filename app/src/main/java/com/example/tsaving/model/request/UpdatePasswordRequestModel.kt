package com.example.tsaving.model.request

import com.google.gson.annotations.SerializedName

data class UpdatePasswordRequestModel(
    @SerializedName("old_password")
    val OldPassword: String,

    @SerializedName("new_password")
    val NewPassword: String
)