package com.example.tsaving


import android.content.Intent
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
//        lifecycle.addObserver(updatePasswordViewModel)

        btn_up_back.setOnClickListener{
            finish()
        }

        updatePasswordViewModel.errorOldPassword.observe(this, Observer { newErrorName -> layout_oldpassword.setError(newErrorName)})
        updatePasswordViewModel.errorNewPassword.observe(this, Observer { newErrorName -> layout_newpassword.setError(newErrorName)})
        updatePasswordViewModel.errorConfirmPassword.observe(this, Observer { newErrorName -> layout_confirmpassword.setError(newErrorName)})

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
//            val intent = Intent(this, ProfileFragment::class.java)
//            startActivity(intent)

            val cek = updatePasswordViewModel.onValidate(oldPassword,newPassword,confirmPassword)

            if(cek == 0){
                val intent = Intent(this, ProfileFragment::class.java)
                startActivity(intent)
            }

            //ErrorDialogHandling(this, "Change Password ", "Password Updated").errorResponseDialog()


        }

    }


}