package com.example.tsaving.vm

import android.util.Patterns
import androidx.lifecycle.*
import com.example.tsaving.model.request.EditProfileRequestModel
import com.example.tsaving.model.response.GenericResponseModel
import com.example.tsaving.model.response.ProfileResponse
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

enum class ErrorName(val message: String) {
    NOTENOUGHLENGTH("Name must consists of minimum 4 characters"),
    MAXLENGTHEXCEEDED("Name must consists of maximum 100 characters"),
    ISBLANK("Name cannot be blank")
}

enum class ErrorEmail(val message: String) {
    NOTENOUGHLENGTH("Email must consists of minimum 5 characters"),
    MAXLENGTHEXCEEDED("Email must consists of maximum 200 characters"),
    ISBLANK("Email cannot be blank"),
    ISINVALID("Invalid email format")
}

enum class ErrorPhone(val message: String) {
    NOTENOUGHLENGTH("Phone number must consists of minimum 10 characters"),
    MAXLENGTHEXCEEDED("Email must consists of maximum 20 characters"),
    ISBLANK("Phone number cannot be blank"),
    ISINVALID("Invalid phone number")
}

enum class ErrorAddress(val message: String) {
    ISBLANK("Address cannot be blank")
}

enum class ErrorNetwork(val message: String) {
    NOCONNECTION("Failed to update, please check your connection"),
}

enum class UpdateStatus() {
    INIT,
    SUCCESS,
    FAILED,
    EMAIL_CHANGED
}

class EditProfileViewModel(private val tsRepo: TsavingRepository) : ViewModel(), CoroutineScope,
    LifecycleObserver {
    private val CHANNEL: String = "Android"

    private var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    private var _errorName = MutableLiveData<String>()
    val errorName: LiveData<String> = _errorName

    private var _errorEmail = MutableLiveData<String>()
    val errorEmail: LiveData<String> = _errorEmail

    private var _errorPhone = MutableLiveData<String>()
    val errorPhone: LiveData<String> = _errorPhone

    private var _errorAddress = MutableLiveData<String>()
    val errorAddress: LiveData<String> = _errorAddress

    private var _errorNetwork = MutableLiveData<String>()
    val errorNetwork: LiveData<String> = _errorNetwork

    private var _loadingPage = MutableLiveData<Boolean>()
    val loadingPage: LiveData<Boolean> = _loadingPage

    private var _loadingButton = MutableLiveData<Boolean>()
    val loadingButton: LiveData<Boolean> = _loadingButton

    private var _updateStatus = MutableLiveData<UpdateStatus>()
    val updateStatus: LiveData<UpdateStatus> = _updateStatus

    private var _data = MutableLiveData<GenericResponseModel<ProfileResponse>>()
    val data: LiveData<GenericResponseModel<ProfileResponse>> = _data

    init {
        _errorName.value = null
        _errorEmail.value = null
        _errorPhone.value = null
        _errorAddress.value = null
        _loadingPage.value = true
        _loadingButton.value = false
        _updateStatus.value = UpdateStatus.INIT
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun fetchProfileData() {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { tsRepo.viewProfile() }
                if (result.status == "SUCCESS") {
                    _data.value = result
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        _errorNetwork.value = ErrorNetwork.NOCONNECTION.message
                    }
                    is HttpException -> {
                        _errorNetwork.value = ErrorNetwork.NOCONNECTION.message
                    }
                }
            }
        }
        _loadingPage.value = false
    }

    fun checkValidity(
        inputName: String,
        inputEmail: String,
        inputPhone: String,
        inputAddress: String
    ): Boolean {
        _loadingButton.value = true
        var isValid = true
        when {
            inputName.isBlank() -> {
                _errorName.value = ErrorName.ISBLANK.message
                isValid = false
            }
            inputName.length < 4 -> {
                _errorName.value = ErrorName.NOTENOUGHLENGTH.message
                isValid = false
            }
            inputName.length > 100 -> {
                _errorName.value = ErrorName.MAXLENGTHEXCEEDED.message
                isValid = false
            }
            else -> {
                _errorName.value = null
            }
        }

        when {
            inputEmail.isBlank() -> {
                _errorEmail.value = ErrorEmail.ISBLANK.message
                isValid = false
            }
            inputEmail.length < 5 -> {
                _errorEmail.value = ErrorEmail.NOTENOUGHLENGTH.message
                isValid = false
            }
            inputName.length > 200 -> {
                _errorEmail.value = ErrorEmail.MAXLENGTHEXCEEDED.message
                isValid = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches() -> {
                _errorEmail.value = ErrorEmail.ISINVALID.message
                isValid = false
            }
            else -> {
                _errorEmail.value = null
            }
        }

        when {
            inputPhone.isBlank() -> {
                _errorPhone.value = ErrorPhone.ISBLANK.message
                isValid = false
            }
            inputPhone.length < 10 -> {
                _errorPhone.value = ErrorPhone.NOTENOUGHLENGTH.message
                isValid = false
            }
            inputPhone.length > 20 -> {
                _errorPhone.value = ErrorPhone.MAXLENGTHEXCEEDED.message
                isValid = false
            }
            !Patterns.PHONE.matcher(inputPhone).matches() -> {
                _errorPhone.value = ErrorPhone.ISINVALID.message
                isValid = false
            }
            else -> {
                _errorPhone.value = null
            }
        }

        when {
            inputAddress.isBlank() -> {
                _errorAddress.value = ErrorAddress.ISBLANK.message
                isValid = false
            }
            else -> {
                _errorAddress.value = null
            }
        }

        _loadingButton.value = false

        return isValid
    }

    fun updateData(
        inputName: String,
        inputEmail: String,
        inputPhone: String,
        inputAddress: String
    ) {
        var request =
            EditProfileRequestModel(inputName, inputEmail, inputPhone, inputAddress, CHANNEL)
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.IO) { tsRepo.updateProfile(request) }
                if (result.status == "SUCCESS") {
                    if (inputEmail != _data.value?.data?.cust_email) {
                        _updateStatus.value = UpdateStatus.EMAIL_CHANGED
                    } else {
                        _updateStatus.value = UpdateStatus.SUCCESS
                    }
                } else {
                    _errorNetwork.value = ErrorNetwork.NOCONNECTION.message
                    _updateStatus.value = UpdateStatus.FAILED
                }

            } catch (t: Throwable) {
                when (t) {
                    is IOException -> {
                        _errorNetwork.value = ErrorNetwork.NOCONNECTION.message
                        _updateStatus.value = UpdateStatus.FAILED
                    }
                    is HttpException -> {
                        _errorNetwork.value = ErrorNetwork.NOCONNECTION.message
                        _updateStatus.value = UpdateStatus.FAILED
                    }
                }
            }
        }
    }
}