package com.example.tsaving.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class ResponseModelList(
    val status: String,
    val message: String,
    val data: List<TransactionHistoryResponseBody>
)

data class TransactionHistoryResponseBody(
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