package com.example.tsaving.model

data class DashboardResponseModel (
    val status: String,
    val message: String,
    val data: DashboardModel
)

data class ResponseModel (
    val status: String,
    val message: String
)
