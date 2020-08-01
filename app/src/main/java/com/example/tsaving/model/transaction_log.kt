package com.example.tsaving.model

import com.google.gson.annotations.SerializedName
import java.util.*

class TransactionLog (
    @SerializedName("tl_id")
    val tlId: Int,
    @SerializedName("account_num")
    val accNum: String,
    @SerializedName("from_account")
    val from: String,
    @SerializedName("dest_account")
    val dest: String,
    @SerializedName("tran_amount")
    val amount: Int,
    @SerializedName("description")
    val desc: String,
    @SerializedName("created_at")
    val createdAt: Date)