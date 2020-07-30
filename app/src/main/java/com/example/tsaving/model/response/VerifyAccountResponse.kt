package com.example.tsaving.model.response

data class VerifyAccountResponseModel(
    val status: String,
    val message: String,
    val data: DataResponse<TokenResponse>
)

data class TokenResponse (
    val token: String
)
