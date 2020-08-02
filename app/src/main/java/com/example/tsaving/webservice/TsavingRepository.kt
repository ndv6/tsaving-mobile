package com.example.tsaving.webservice

import com.example.tsaving.BaseApplication
import com.example.tsaving.model.DashboardResponseModel
import com.example.tsaving.model.request.*
import com.example.tsaving.model.response.GenericResponseModel
import com.example.tsaving.model.response.EditVaResponse

class TsavingRepository {
    private var webService: WebServices = webServices
    private var tnotifService: WebServices = tnotifServices

    suspend fun register(body: RegisterRequestModel) = webService.register(body)
    suspend fun login(body: LoginRequestModel) = webService.login(body)
    suspend fun verifyAccount(body: VerifyRequestModel) = webService.verifyAccount(body)
    suspend fun updatePassword(body: UpdatePasswordRequestModel) = webService.updatePassword(BaseApplication.token, body)
    suspend fun viewProfile() = webService.viewProfile(BaseApplication.token)
    suspend fun updateProfile(body: EditProfileRequestModel) = webService.updateProfile(BaseApplication.token, body)
    suspend fun dashboard() : DashboardResponseModel = webService.dashboard(BaseApplication.token)
    suspend fun updatePhoto() = webService.updatePhoto()
    suspend fun listVa() = webService.listVa(BaseApplication.token)
    suspend fun createVa(body: AddVaRequestModel) : GenericResponseModel<Any> = webService.createVa(body,BaseApplication.token)
    suspend fun transferVa(body: TransferToVaRequestModel) = webService.transferToVa(BaseApplication.token, body)
    suspend fun updateVa(vaNum: String, body: EditVaRequestModel) : EditVaResponse = webService.updateVa(vaNum, body, BaseApplication.token)
    suspend fun transferVaToMainAccount(vaNum: String) = webService.transferVaToMainAccount(vaNum)
    suspend fun deleteVa(vaNum: String) = webService.deleteVa(vaNum)
    suspend fun listTransactionHistory(page: Int) = webService.listTransactionHistory(page)
    suspend fun sendEmail(body: SendMailRequest) = tnotifService.sendEmail(body)
    suspend fun getToken(body: GetTokenRequest) = webService.getToken(body)
}
