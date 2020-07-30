package com.example.tsaving.webservice

import com.example.tsaving.model.request.AddVaRequestModel
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.model.request.VerifyRequestModel

class TsavingRepository {
    private var webService: WebServices = webServices

    suspend fun register() = webService.register()
    suspend fun login(body: LoginRequestModel) = webService.login(body)
    suspend fun verifyAccount(body: VerifyRequestModel) = webService.verifyAccount(body)
    suspend fun viewProfile() = webService.viewProfile()
    suspend fun updateProfile() = webService.updateProfile()
    suspend fun dashboard() = webService.dashboard()
    suspend fun updatePhoto() = webService.updatePhoto()
    suspend fun transferVa() = webService.transferToVa()
    suspend fun listVa() = webService.listVa()
    suspend fun createVa(body: AddVaRequestModel) = webService.createVa(body)
    suspend fun updateVa(vaNum: String) = webService.updateVa(vaNum)
    suspend fun transferVaToMainAccount(vaNum: String) = webService.transferVaToMainAccount(vaNum)
    suspend fun deleteVa(vaNum: String) = webService.deleteVa(vaNum)
    suspend fun listTransactionHistory(page: Int) = webService.listTransactionHistory(page)
    suspend fun sendEmail() = webService.sendEmail()
}