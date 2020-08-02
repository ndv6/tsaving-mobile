package com.example.tsaving.model.response

import com.example.tsaving.model.DashboardModel
import org.json.JSONObject

data class DashboardResponseModel (
    val status: String,
    val message: String,
    val data: DashboardModel
)

data class ResponseModel (
    val status: String,
    val message: String,
    val data: JSONObject
)
