package com.example.tsaving.webservice

import android.content.Context
import android.content.Intent
import com.example.tsaving.BaseApplication
import com.example.tsaving.LoginActivity
import com.example.tsaving.model.ResponseModel
import com.example.tsaving.model.request.LoginRequestModel
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Response
import com.example.tsaving.model.request.VerifyRequestModel
import com.example.tsaving.model.response.VerifyAccountResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


const val contentType = "Content-Type: application/json"
const val jwtAuth = "Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjdXN0X2lkIjoxMywiYWNjb3VudF9udW0iOiIyMDA3MjYyOTI1IiwiZXhwaXJlZCI6IjIwMjAtMDctMzBUMTM6MDg6MDkuNTQ1NzgrMDc6MDAifQ.YtDrManolqO4-VH6hf-3bzIC1qEw52uaKvq3JQF6qgU"
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
    suspend fun verifyAccount(@Body body: VerifyRequestModel): VerifyAccountResponseModel

    @GET(VIEW_PROFILE)
    suspend fun viewProfile()

    @PUT(UPDATE_PROFILE)
    suspend fun updateProfile()

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

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        set your login token
        var req = chain.request()
        req = req.newBuilder().header("Content-Type", "application/json")
            .header("User-Agent", "tsaving-mobile")
            .header("Accept", "application/json")
            .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJjdXN0X2lkIjoxMywiYWNjb3VudF9udW0iOiIyMDA3MjYyOTI1IiwiZXhwaXJlZCI6IjIwMjAtMDctMzBUMTI6NTc6MzguOTkyOTA5KzA3OjAwIn0.gqGeZmIniWQ1ser3u8JEAAsyFs1u4MUmPeDSMZlKTCQ")
            .build()
        return chain.proceed(req)
    }
}

class UnauthInterceptor (val ctx: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val res = chain.proceed(chain.request())

        if (res.code == 401) {
            val intent = Intent(ctx, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            ctx.startActivity(intent)
        }
        return res
    }
}

val ohc = OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).addInterceptor(UnauthInterceptor(BaseApplication.appContext)).build()


//singleton
val webServices: WebServices by lazy {
    Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(ohc)
        .build()
        .create(WebServices::class.java)
}