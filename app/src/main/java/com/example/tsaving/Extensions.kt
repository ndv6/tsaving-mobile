package com.example.tsaving

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

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