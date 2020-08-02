package com.example.tsaving.webservice

import android.content.Context
import android.content.Intent
import com.example.tsaving.BaseApplication
import com.example.tsaving.LoginActivity
import com.example.tsaving.model.DashboardResponseModel
import com.example.tsaving.model.request.AddVaRequestModel
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.model.request.RegisterRequestModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import com.example.tsaving.model.request.VerifyRequestModel
import com.example.tsaving.model.response.*
import com.example.tsaving.model.response.AddVaResponseModel
import com.example.tsaving.model.response.LoginResponseModel
import com.example.tsaving.model.response.RegisterResponse
import com.example.tsaving.model.response.EmailResponse
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
    suspend fun register(@Body body: RegisterRequestModel): RegisterResponse

    @POST(LOGIN)
    suspend fun login(@Body body: LoginRequestModel): GenericResponseModel<DataLogin>

    @POST(VERIFY_ACCOUNT)
    suspend fun verifyAccount(@Body body: VerifyRequestModel): GenericResponseModel<EmailResponse>

    @GET(VIEW_PROFILE)
    suspend fun viewProfile()

    @PUT(UPDATE_PROFILE)
    suspend fun updateProfile()

    @GET(DASHBOARD)
    suspend fun dashboard(@Header("Authorization") token: String) : DashboardResponseModel

    @PATCH(UPDATE_PHOTO)
    suspend fun updatePhoto()

    @POST(TRANSFER_VA)
    suspend fun transferToVa()

    @GET(LIST_VA)
    suspend fun listVa()

    @POST(CREATE_VA)
    suspend fun createVa(@Body body:AddVaRequestModel) : AddVaResponseModel

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
//        token parse from parameter
        var req = chain.request()
        req = req.newBuilder().header("Content-Type", "application/json")
            .header("User-Agent", "tsaving-mobile")
            .header("Accept", "application/json")
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