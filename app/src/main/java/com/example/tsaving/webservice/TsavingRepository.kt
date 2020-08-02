package com.example.tsaving.webservice

import com.example.tsaving.BaseApplication
import com.example.tsaving.model.DashboardResponseModel
import com.example.tsaving.model.request.AddVaRequestModel
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.model.request.VerifyRequestModel
import com.example.tsaving.model.response.AddVaResponseModel
import com.example.tsaving.model.response.GenericResponseModel
import retrofit2.http.Body
import com.example.tsaving.model.request.RegisterRequestModel

class TsavingRepository {
    private var webService: WebServices = webServices

    suspend fun register(body: RegisterRequestModel) = webService.register(body)
    suspend fun login(body: LoginRequestModel) = webService.login(body)
    suspend fun verifyAccount(body: VerifyRequestModel) = webService.verifyAccount(body)
    suspend fun viewProfile() = webService.viewProfile()
    suspend fun updateProfile() = webService.updateProfile()
    suspend fun dashboard() : DashboardResponseModel = webService.dashboard(BaseApplication.token)
    suspend fun updatePhoto() = webService.updatePhoto()
    suspend fun transferVa() = webService.transferToVa()
    suspend fun listVa() = webService.listVa()
    suspend fun createVa(body: AddVaRequestModel) : GenericResponseModel<AddVaResponseModel> = webService.createVa(body,BaseApplication.token)
    suspend fun updateVa(vaNum: String) = webService.updateVa(vaNum)
    suspend fun transferVaToMainAccount(vaNum: String) = webService.transferVaToMainAccount(vaNum)
    suspend fun deleteVa(vaNum: String) = webService.deleteVa(vaNum)
    suspend fun listTransactionHistory(page: Int) = webService.listTransactionHistory(page)
    suspend fun sendEmail() = webService.sendEmail()
}
