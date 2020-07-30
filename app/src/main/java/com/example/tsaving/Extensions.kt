
package com.example.tsaving

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

fun validateName(actionError: () -> Unit) : Boolean{
    return  true
}

sealed class ErrorName{
    object NotValidLength : ErrorName()
    object NotAlphaNumeric : ErrorName()
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