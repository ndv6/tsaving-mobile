package com.example.tsaving

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.*

fun EditText.afterTextChanged(afterText: (String) -> Unit){
    addTextChangedListener(object : TextWatcher{
        override fun afterTextChanged(editable: Editable?) {
            afterText(editable?.toString() ?: "")
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //Not yet implemented
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            //Not yet implemented
        }
    })
}

sealed class ErrorName{
    object NotValidLength : ErrorName()
    object NotAlphaNumeric : ErrorName()
    object NullAmount : ErrorName()
    object InvalidLogin : ErrorName()
    object NullEmail : ErrorName()
    object NullPassword : ErrorName()
    object InvalidEmail : ErrorName()
    object ErrorNetwork : ErrorName()
    object ErrorBadRequest : ErrorName()
    object NullEmailAndPass : ErrorName()
}
//Thousand separator func
fun String.FormatDecimal() : String{
    var stringAmount = this.replace(",", "")
    if(stringAmount.length > 3){
        stringAmount = NumberFormat.getNumberInstance(Locale.getDefault()).format(stringAmount.toDouble())
    }
    return stringAmount
}

fun String.IsEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}