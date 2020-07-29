package com.example.tsaving.webservice

import com.example.tsaving.model.request.LoginRequestModel

class TsavingRepository {
    private var webService: WebServices = webServices

    suspend fun register() = webService.register()
    suspend fun login(body: LoginRequestModel) = webService.login(body)
    suspend fun verifyAccount() =
        webService.verifyAccount()

    suspend fun viewProfile() =
        webService.viewProfile()

    suspend fun updateProfile() =
        webService.updateProfile()

    suspend fun dashboard() = webService.dashboard()
    suspend fun updatePhoto() =
        webService.updatePhoto()

    suspend fun transferVa() = webService.transferToVa()
    suspend fun listVa() = webService.listVa()
    suspend fun createVa() = webService.createVa()
    suspend fun updateVa(vaNum: String) =
        webService.updateVa(vaNum)

    suspend fun transferVaToMainAccount() =
        webService.transferVaToMainAccount()

    suspend fun deleteVa(vaNum: String) =
        webService.deleteVa(vaNum)

    suspend fun listTransactionHistory(page: Int) =
        webService.listTransactionHistory(page)

    suspend fun sendEmail() = webService.sendEmail()


}