package com.example.tsaving

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class ErrorDialogHandling(ctx: Context, title: String, message: String) {

    val ctx = ctx
    val title = title
    val message = message

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        //aksi yg mau dilakukan setelah button di klik
        //refetch api
    }

    fun errorResponseDialog(){
        val builder = AlertDialog.Builder(ctx)
        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setPositiveButton("REFRESH", DialogInterface.OnClickListener(function = positiveButtonClick))
            show()
        }
    }
}