package com.example.tsaving.webservice

import com.example.tsaving.model.ResponseModel
import com.example.tsaving.model.request.LoginRequestModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val jwtAuth = "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjdXN0X2lkIjoxMywiYWNjb3VudF9udW0iOiIyMDA3MjYyOTI1IiwiZXhwaXJlZCI6IjIwMjAtMDctMzBUMDE6MTY6NDQuMDU3MjI5KzA3OjAwIn0.YtcQp_0IxXNR4wseH90hZqBtC33ro8YRB66koK2Le-k"
const val contentType = "Content-Type: application/json"
const val accept = "Accept: application/json"

interface WebServices {
    companion object {
        const val LOGIN = "login"
        const val REGISTER = "register"
        const val DASHBOARD = "me/dashboard"
        const val VERIFY_ACCOUNT = "verify-account"
        const val VIEW_PROFILE = "me/profile"
        const val UPDATE_PHOTO = "me/update-photo"
        const val UPDATE_PROFILE = "me/update"
        const val TRANSFER_VA = "me/transfer-va"
        const val LIST_VA = "me/va"
        const val CREATE_VA = "me/va/create"
        const val UPDATE_VA = "me/va/{va_num}/update"
        const val TRANSFER_VA_TO_MAIN_ACCOOUNT = "me/va/{va_num}/transfer-main"
        const val DELETE_VA = "me/va/{va_num}"
        const val LIST_TRANSACTION_HISTORY = "me/transaction/{page}"
        const val SEND_EMAIL = "sendMail"
    }

    @POST(REGISTER)
    suspend fun register()

    @POST(LOGIN)
    suspend fun login(@Body body: LoginRequestModel): ResponseModel

    @POST(VERIFY_ACCOUNT)
    suspend fun verifyAccount()

    @GET(VIEW_PROFILE)
    suspend fun viewProfile()

    @PUT(UPDATE_PROFILE)
    suspend fun updateProfile()

    @Headers(jwtAuth, contentType, accept)
    @GET(DASHBOARD)
    suspend fun dashboard() : ResponseModel

    @PATCH(UPDATE_PHOTO)
    suspend fun updatePhoto()

    @POST(TRANSFER_VA)
    suspend fun transferToVa()

    @GET(LIST_VA)
    suspend fun listVa()

    @POST(CREATE_VA)
    suspend fun createVa()

    @PUT(UPDATE_VA)
    suspend fun updateVa(@Path("va_num") vaNum: String)

    @POST(TRANSFER_VA_TO_MAIN_ACCOOUNT)
    suspend fun transferVaToMainAccount(@Path("va_num") vaNum: String)

    @POST(DELETE_VA)
    suspend fun deleteVa(@Path("va_num") vaNum: String)

    @DELETE(LIST_TRANSACTION_HISTORY)
    suspend fun listTransactionHistory(@Path("page") page: Int)

    @POST(SEND_EMAIL)
    suspend fun sendEmail()
}

//singleton
val webServices: WebServices by lazy {
    Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WebServices::class.java)
}