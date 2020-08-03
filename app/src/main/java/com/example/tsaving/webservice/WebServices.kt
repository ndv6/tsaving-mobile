package com.example.tsaving.webservice

import android.content.Context
import android.content.Intent
import com.example.tsaving.BaseApplication
import com.example.tsaving.LoginActivity
import com.example.tsaving.model.request.*
import com.example.tsaving.model.request.AddVaRequestModel
import com.example.tsaving.model.request.EditProfileRequestModel
import com.example.tsaving.model.request.EditVaRequestModel
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.model.request.UpdatePasswordRequestModel
import com.example.tsaving.model.response.UpdatePasswordResponseModel
import com.example.tsaving.vm.UpdatePasswordViewModel
import com.example.tsaving.model.request.TransferToVaRequestModel
import com.example.tsaving.model.request.RegisterRequestModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import com.example.tsaving.model.response.*
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
        const val UPDATE_PASSWORD = "me/update-password"
        const val TRANSFER_VA = "me/transfer-va"
        const val LIST_VA = "me/va"
        const val CREATE_VA = "me/va/create"
        const val UPDATE_VA = "me/va/{va_num}/update"
        const val TRANSFER_VA_TO_MAIN_ACCOOUNT = "me/va/{va_num}/transfer-main"
        const val DELETE_VA = "me/va/{va_num}"
        const val LIST_TRANSACTION_HISTORY = "me/transaction/{page}"
        const val SEND_EMAIL = "sendMail"
        const val GET_TOKEN = "get-token"
    }

    @POST(REGISTER)
    suspend fun register(@Body body: RegisterRequestModel): GenericResponseModel<RegisterResponse>

    @POST(LOGIN)
    suspend fun login(@Body body: LoginRequestModel): GenericResponseModel<DataLogin>

    @POST(VERIFY_ACCOUNT)
    suspend fun verifyAccount(@Body body: VerifyRequestModel): GenericResponseModel<EmailResponse>

    @GET(VIEW_PROFILE)
    suspend fun viewProfile(@Header("Authorization") token: String) : GenericResponseModel<ProfileResponse>

    @PUT(UPDATE_PROFILE)
    suspend fun updateProfile(@Header("Authorization") token: String, @Body body: EditProfileRequestModel) : GenericResponseModel<Any>

    @PATCH(UPDATE_PASSWORD)
    suspend fun  updatePassword(@Header("Authorization") token: String, @Body body: UpdatePasswordRequestModel): GenericResponseModel<Any>

    @GET(DASHBOARD)
    suspend fun dashboard(@Header("Authorization") token: String) : DashboardResponseModel

    @PATCH(UPDATE_PHOTO)
    suspend fun updatePhoto()

    @PUT(TRANSFER_VA)
    suspend fun transferToVa(@Header("Authorization") token: String, @Body body: TransferToVaRequestModel): GenericResponseModel<Any>

    @GET(LIST_VA)
    suspend fun listVa(@Header("Authorization") token: String)

    @POST(CREATE_VA)
    suspend fun createVa(@Body body:AddVaRequestModel, @Header("Authorization") token: String) : GenericResponseModel<Any>

    @PUT(UPDATE_VA)
    suspend fun updateVa(
        @Path("va_num") vaNum: String,
        @Body body: EditVaRequestModel,
        @Header("Authorization") token: String
    ) : EditVaResponse

    @POST(TRANSFER_VA_TO_MAIN_ACCOOUNT)
    suspend fun transferVaToMainAccount(@Path("va_num") vaNum: String,@Body body:TransferToMainRequestModel, @Header("Authorization") token: String ) : GenericResponseModel<Any>

    @POST(DELETE_VA)
    suspend fun deleteVa(@Path("va_num") vaNum: String)

    @GET(LIST_TRANSACTION_HISTORY)
    suspend fun listTransactionHistory(
        @Header("Authorization") token: String,
        @Path("page") page: Int
    ): GenericResponseModel<List<TransactionHistoryResponseData>>


    @POST(SEND_EMAIL)
    suspend fun sendEmail(@Body body: SendMailRequest) : GenericResponseModel<Any>

    @POST(GET_TOKEN)
    suspend fun getToken(@Body body: GetTokenRequest) : GenericResponseModel<GetTokenResponse>
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

class TnotifHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
//        token parse from parameter
        var req = chain.request()
        req = req.newBuilder().header("Content-Type", "application/json")
            .header("User-Agent", "tnotif")
            .header("Accept", "application/json")
            .build()
        return chain.proceed(req)
    }
}

val ohc = OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).addInterceptor(UnauthInterceptor(BaseApplication.appContext)).build()
val tnotif_ohc = OkHttpClient.Builder().addInterceptor(TnotifHeaderInterceptor()).build()

//singleton
val webServices: WebServices by lazy {
    Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(ohc)
        .build()
        .create(WebServices::class.java)
}

val tnotifServices: WebServices by lazy {
    Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8082/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(tnotif_ohc)
        .build()
        .create(WebServices::class.java)
}