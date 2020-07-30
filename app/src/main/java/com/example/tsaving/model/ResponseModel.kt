package com.example.tsaving.model

data class ResponseModel(
    val status: String,
    val message: String,
    val data: DashboardResponse
)

data class DashboardResponse (
    val cust_name: String,
    val cust_email: String,
    val account_num: String,
    val account_balance: Int,
    val virtual_accounts: List<VirtualAccount>
)