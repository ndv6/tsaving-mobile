package com.example.tsaving.model.response

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class TransactionHistoryResponseData(
    @SerializedName("account_num")
    val accountNum: String,

    @SerializedName("from_account")
    val fromAccount: String,

    @SerializedName("dest_account")
    val destinationAccount: String,

    @SerializedName("tran_amount")
    val transferAmount: Int,

    val description: String,

    @SerializedName("created_at")
    val createdAt: Timestamp
)