package com.example.tsaving.model.response

data class ProfileResponseModel(
    val status: String,
    val message: String,
    val data: CustomerResponse
)

data class CustomerResponse(
    val cust_id: Int,
    val account_num: String,
    val cust_name: String,
    val cust_address: String,
    val cust_phone: String,
    val cust_email: String,
    val cust_password: String,
    val cust_pict: String,
    val is_verified: Boolean,
    val channel: String,
    val created_at: String,
    val updated_at: String
)