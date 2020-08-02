package com.example.tsaving.model.response

data class GenericResponseModel<T>(val status: String, val message: String, val data: T){

}
