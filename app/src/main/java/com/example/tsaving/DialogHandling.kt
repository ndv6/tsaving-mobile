package com.example.tsaving

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class DialogHandling : Activity() {

//    DialogHandling().basicAlert(this@TransferActivity, "title", "message", "btn_txt")


    val positiveButtonClick = { dialog: DialogInterface, which: Int ->

    }

    fun basicAlert(ctx: Context, title: String, message: String, button: String){
        val builder = AlertDialog.Builder(ctx)
        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setPositiveButton(button, DialogInterface.OnClickListener(function = positiveButtonClick))
            show()
        }
    }

}