package com.example.tsaving.model

import org.json.JSONObject

data class ResponseModel(
    val status: String,
    val message: String,
    val data: JSONObject
)