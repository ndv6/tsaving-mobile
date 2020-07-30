package com.example.tsaving.vm

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

enum class ErrorName(val message: String) {
    NOTENOUGHTLENGTH("Name must consists of minimum 4 characters"),
    MAXLENGTHEXCEEDED("Name must consists of maximum 100 characters"),
    ISBLANK("Name cannot be blank")
}

enum class ErrorEmail(val message: String) {
    NOTENOUGHTLENGTH("Email must consists of minimum 5 characters"),
    MAXLENGTHEXCEEDED("Email must consists of maximum 200 characters"),
    ISBLANK("Email cannot be blank"),
    ISINVALID("Invalid email format")
}

enum class ErrorPhone(val message: String) {
    NOTENOUGHTLENGTH("Phone number must consists of minimum 10 characters"),
    MAXLENGTHEXCEEDED("Email must consists of maximum 20 characters"),
    ISBLANK("Phone number cannot be blank"),
    ISINVALID("Invalid phone number")
}

enum class ErrorAddress(val message: String) {
    NOTENOUGHTLENGTH("Address must consists of minimum 10 characters"),
    MAXLENGTHEXCEEDED("Email must consists of maximum 200 characters"),
    ISBLANK("Address cannot be blank")
}

class EditProfileViewModel : ViewModel(), LifecycleObserver {
    var accNum: String = "2007160004"

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _address = MutableLiveData<String>()
    val address: LiveData<String> = _address



    private val _errorName = MutableLiveData<String>()
    val errorName: LiveData<String> = _errorName

    private val _errorEmail = MutableLiveData<String>()
    val errorEmail: LiveData<String> = _errorEmail

    private val _errorPhone = MutableLiveData<String>()
    val errorPhone: LiveData<String> = _errorPhone

    private val _errorAddress = MutableLiveData<String>()
    val errorAddress: LiveData<String> = _errorAddress

    init {
        _name.value = "Sukirman"
        _email.value = "sukirman@sukijan.com"
        _phone.value = "085252525252"
        _address.value = "Jalan Mega Kuningan"

        _errorName.value = null
        _errorEmail.value = null
        _errorPhone.value = null
        _errorAddress.value = null
    }

    fun checkValidity(
        inputName: String,
        inputEmail: String,
        inputPhone: String,
        inputAddress: String
    ) : Boolean {
        var isValid = true
        when {
            inputName.isBlank() -> {
                _errorName.value  = ErrorName.ISBLANK.message
                isValid = false
            }
            inputName.length < 4 -> {
                _errorName.value  = ErrorName.NOTENOUGHTLENGTH.message
                isValid = false
            }
            inputName.length > 100 -> {
                _errorName.value  = ErrorName.MAXLENGTHEXCEEDED.message
                isValid = false
            }
            else -> {
                _errorName.value = null
            }
        }

        when {
            inputEmail.isBlank() -> {
                _errorEmail.value  = ErrorEmail.ISBLANK.message
                isValid = false
            }
            inputEmail.length < 5 -> {
                _errorEmail.value  = ErrorEmail.NOTENOUGHTLENGTH.message
                isValid = false
            }
            inputName.length > 200 -> {
                _errorEmail.value  = ErrorEmail.MAXLENGTHEXCEEDED.message
                isValid = false
            }
            !Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches() -> {
                _errorEmail.value  = ErrorEmail.ISINVALID.message
                isValid = false
            }
            else -> {
                _errorEmail.value = null
            }
        }

        when {
            inputPhone.isBlank() -> {
                _errorPhone.value  = ErrorPhone.ISBLANK.message
                isValid = false
            }
            inputPhone.length < 10 -> {
                _errorPhone.value  = ErrorPhone.NOTENOUGHTLENGTH.message
                isValid = false
            }
            inputPhone.length > 20 -> {
                _errorPhone.value  = ErrorPhone.MAXLENGTHEXCEEDED.message
                isValid = false
            }
            !Patterns.PHONE.matcher(inputPhone).matches() -> {
                _errorPhone.value  = ErrorPhone.ISINVALID.message
                isValid = false
            }
            else -> {
                _errorPhone.value  = null
            }
        }

        when {
            inputAddress.isBlank() -> {
                _errorAddress.value  = ErrorAddress.ISBLANK.message
                isValid = false
            }
            inputAddress.length < 10 -> {
                _errorAddress.value  = ErrorAddress.NOTENOUGHTLENGTH.message
                isValid = false
            }
            inputAddress.length > 200 -> {
                _errorAddress.value  = ErrorAddress.MAXLENGTHEXCEEDED.message
                isValid = false
            }
            else -> {
                _errorAddress.value  = null
            }
        }

        return isValid
    }
}