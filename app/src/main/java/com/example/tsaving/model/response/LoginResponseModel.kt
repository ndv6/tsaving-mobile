package com.example.tsaving.model.response

data class LoginResponseModel(
    val status: String,
    val message: String,
    val data: TokenResponse
)