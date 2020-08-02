package com.example.tsaving

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.tsaving.vm.UpdatePasswordViewModel
import kotlinx.android.synthetic.main.activity_update_password.*


class UpdatePasswordActivity : AppCompatActivity(), LifecycleOwner {
    private val updatePasswordViewModel : UpdatePasswordViewModel = UpdatePasswordViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        btn_up_back.setOnClickListener{
            finish()
        }

        updatePasswordViewModel.errorOldPassword.observe(this, Observer { newErrorName -> layout_oldpassword.setError(newErrorName)})
        updatePasswordViewModel.errorNewPassword.observe(this, Observer { newErrorName -> layout_newpassword.setError(newErrorName)})
        updatePasswordViewModel.errorConfirmPassword.observe(this, Observer { newErrorName -> layout_confirmpassword.setError(newErrorName)})

        updatePasswordViewModel.status.observe(this, Observer { it ->
            if(it == true) {
                AlertDialog.Builder(this)
                    .setTitle("Success")
                    .setMessage("Password Updated Successfully")
                    .setPositiveButton(
                        "OK"
                    ) { dialog, which -> finish() }
                    .show()
            } else {
                ErrorDialogHandling(this@UpdatePasswordActivity,"Failed", "Unable to Update Password").errorResponseDialog()
            }
        })

        et_up_oldpassword?.afterTextChanged{
                if (et_up_oldpassword.text.toString().length < 6) {
                    layout_oldpassword.setError("Minimum password length is 6")
                }else {
                    layout_oldpassword.setError(null)
                }
        }

        et_up_newpassword?.afterTextChanged{
            if (et_up_newpassword.text.toString().length < 6) {
                layout_newpassword.setError("Minimum password length is 6")
            }else {
                layout_newpassword.setError(null)
            }
        }

        et_up_confirmpassword?.afterTextChanged{
            if (et_up_confirmpassword.text.toString().length < 6) {
                layout_confirmpassword.setError("Minimum password length is 6")
            }else {
                layout_confirmpassword.setError(null)
            }
        }

        btn_up_updatepassword.setOnClickListener {

            val oldPassword = et_up_oldpassword.text.toString()
            val newPassword = et_up_newpassword.text.toString()
            val confirmPassword = et_up_confirmpassword.text.toString()

            val cek = updatePasswordViewModel.onValidate(oldPassword,newPassword,confirmPassword)

        }

    }


}