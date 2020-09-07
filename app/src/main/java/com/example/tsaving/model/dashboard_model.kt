package com.example.tsaving.model

import com.example.tsaving.model.Account
import com.example.tsaving.model.VirtualAccount
import com.google.gson.annotations.SerializedName
import java.text.DateFormat

class DashboardModel (
    @SerializedName("cust_name")
    val custName: String,
    @SerializedName("cust_email")
    val custEmail: String,
    @SerializedName("virtual_accounts")
    val virtualAccounts: List<VirtualAccount>,
    @SerializedName("account_num")
    val accountNum: String,
    @SerializedName("account_balance")
    val accountBalance: Int,
    @SerializedName("card_num")
    val cardNumber: String,
    @SerializedName("cvv")
    val cvv: String,
    @SerializedName("expired")
    val expired: String
    )

