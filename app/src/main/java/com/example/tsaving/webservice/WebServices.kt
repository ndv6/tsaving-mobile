package com.example.tsaving.webservice

import com.example.tsaving.model.ResponseModel
import com.example.tsaving.model.request.LoginRequestModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


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

    @GET(DASHBOARD)
    suspend fun dashboard()

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
    suspend fun transferVaToMainAccount()

    @POST(DELETE_VA)
    suspend fun deleteVa(@Path("va_num") vaNum: String)

    @DELETE(LIST_TRANSACTION_HISTORY)
    suspend fun listTransactionHistory(@Path("page") page: Int)

    @POST(SEND_EMAIL)
    suspend fun sendEmail()
}

val webServices: WebServices by lazy {
    Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WebServices::class.java)

}