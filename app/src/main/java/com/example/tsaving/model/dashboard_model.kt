package com.example.tsaving.model

import com.example.tsaving.model.Account
import com.example.tsaving.model.VirtualAccount
import com.google.gson.annotations.SerializedName

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
    val accountBalance: Int)

