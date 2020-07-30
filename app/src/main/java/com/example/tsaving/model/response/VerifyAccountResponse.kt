package com.example.tsaving.model.response

data class VerifyAccountResponseModel(
    val status: String,
    val message: String,
    val data: TokenResponse
)

data class TokenResponse (
    val token: String
)