package com.example.tsaving.model.response

data class GetTokenResponse(
    val et_id: Int,
    val token: String,
    val email: String
)