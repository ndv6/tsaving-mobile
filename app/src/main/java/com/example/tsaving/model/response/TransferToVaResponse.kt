package com.example.tsaving.model.response

data class TransferToVaResponse(
    val status: String,
    val message: String,
    val data: TokenResponse
)
