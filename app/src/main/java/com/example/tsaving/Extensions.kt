package com.example.tsaving

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
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
    object NetworkError : ErrorName()
    object FailedToRecognizeOTP : ErrorName()
    object NullOTP : ErrorName()
    object NullAmount : ErrorName()
    object InvalidLogin : ErrorName()
    object InvalidRegister : ErrorName()
    object NullName : ErrorName()
    object NullPhone : ErrorName()
    object NullAddress : ErrorName()
    object NullEmail : ErrorName()
    object NullPassword : ErrorName()
    object InvalidEmail : ErrorName()
    object ErrorNetwork : ErrorName()
    object ErrorBadRequest : ErrorName()
    object NullEmailAndPass : ErrorName()
    object InvalidTransferToVA : ErrorName()
    object InvalidTransferToMain :ErrorName()
    object LoginUnAuthorized : ErrorName()
    object ErrorSendMail : ErrorName()
    object  Null: ErrorName()
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

fun Int.currencyFormatter(currencyCode: String): String {
    val currency = Currency.getInstance(currencyCode)
    val format = NumberFormat.getCurrencyInstance()
    format.maximumFractionDigits = currency.defaultFractionDigits
    format.currency = currency
    return format.format(this)
}

fun TextView.formatTransactionAmount(desc: String) {
    /* Placeholder text is used to prevent lint.
        the IDE does not allow concatenating string on TextView's setText
    */
    val placeholderString: String
    when (desc) {
        "DEPOSIT_TO_MAIN_ACCOUNT" -> {
            placeholderString = "+${this.text}"
            this.text = placeholderString
            this.setTextColor(resources.getColor(R.color.colorDarkGreen))
        }

        "MAIN_TO_VA" -> {
            placeholderString = "-${this.text}"
            this.text = placeholderString
            this.setTextColor(resources.getColor(R.color.colorRed))
        }

        "VA_TO_MAIN" -> {
            placeholderString = "+${this.text}"
            this.text = placeholderString
            this.setTextColor(resources.getColor(R.color.colorGreen))
        }
    }
}