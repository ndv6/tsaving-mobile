package com.example.tsaving.webservice

import com.example.tsaving.BaseApplication
import com.example.tsaving.model.DashboardResponseModel
import com.example.tsaving.model.request.*
import com.example.tsaving.model.response.AddVaResponseModel
import com.example.tsaving.model.response.GenericResponseModel
import retrofit2.http.Body

class TsavingRepository {
    private var webService: WebServices = webServices

    suspend fun register(body: RegisterRequestModel) = webService.register(body)
    suspend fun login(body: LoginRequestModel) = webService.login(body)
    suspend fun verifyAccount(body: VerifyRequestModel) = webService.verifyAccount(body)
    suspend fun viewProfile() = webService.viewProfile(BaseApplication.token)
    suspend fun updateProfile(body: EditProfileRequestModel) = webService.updateProfile(BaseApplication.token, body)
    suspend fun dashboard() : DashboardResponseModel = webService.dashboard(BaseApplication.token)
    suspend fun updatePhoto() = webService.updatePhoto()
    suspend fun createVa(body: AddVaRequestModel) : GenericResponseModel<Any> = webService.createVa(body,BaseApplication.token)
    suspend fun transferVa(body: TransferToVaRequestModel) = webService.transferToVa(BaseApplication.token, body)
    suspend fun listVa() = webService.listVa()
    suspend fun updateVa(vaNum: String) = webService.updateVa(vaNum)
    suspend fun transferVaToMainAccount(vaNum: String, body:TransferToMainRequestModel) = webService.transferVaToMainAccount(vaNum,body,BaseApplication.token)
    suspend fun deleteVa(vaNum: String) = webService.deleteVa(vaNum)
    suspend fun listTransactionHistory(page: Int) = webService.listTransactionHistory(page)
    suspend fun sendEmail() = webService.sendEmail()
}
