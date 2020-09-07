package com.example.tsaving.model

import com.google.gson.annotations.SerializedName
import java.util.*

class Account (
    @SerializedName("account_id")
    val accountId: Int?,
    @SerializedName("account_num")
    val accountNum: String?,
    @SerializedName("account_balance")
    val accountBalance: Int?,
    @SerializedName("created_at")
    val createdAt: Date)
