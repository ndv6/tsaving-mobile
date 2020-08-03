package com.example.tsaving

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

class DialogHandling(val callback: () -> Unit) {
    fun basicAlert(ctx: Context, title: String, message: String, button: String){
        val positiveButtonClick = { dialog: DialogInterface, which: Int -> callback()}
        val builder = AlertDialog.Builder(ctx)
        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setPositiveButton(button, DialogInterface.OnClickListener(function = positiveButtonClick))
            show()
        }
    }
    fun ApprovalDialog(ctx: Context, title: String, message: String, positiveButton: String, negativeButton: String) {
        val positiveButtonClick = { dialog: DialogInterface, which: Int -> callback()}
        val negativeButtonClick = { dialog: DialogInterface, which: Int -> dialog.dismiss()}
        val builder = AlertDialog.Builder(ctx)
        with(builder)
        {
            setTitle(title)
            setMessage(message)
            setPositiveButton(positiveButton, DialogInterface.OnClickListener(function = positiveButtonClick))
            setNegativeButton(negativeButton, DialogInterface.OnClickListener(function = negativeButtonClick))
            show()
        }
    }
}