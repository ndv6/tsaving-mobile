package com.example.tsaving.model.response

data class RegisterResponse(
    val status: String,
    val message: String,
    val data: Register
)

data class Register(
    val email: String
)