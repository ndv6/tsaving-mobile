package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.tsaving.vm.RegisterViewModel
import kotlinx.android.synthetic.main.activity_add_va.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.Error


class RegisterActivity: AppCompatActivity(), LifecycleOwner {

    private val registerViewModel: RegisterViewModel = RegisterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_register_signin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerViewModel.flagStatus.observe(this, Observer{ it ->
            if (it == ErrorName.NullName){ il_register_name.setError("Please Input Name Field")}

            if (it == ErrorName.NullAddress){ il_register_address.setError("Please Input Address Field") }
            if (it == ErrorName.NullPhone){ il_register_phone.setError("Please Input Phone Field") }

            if (it == ErrorName.NullEmail){ il_register_email.setError("Please Input Email Field") }
            else if(it == ErrorName.InvalidEmail){ il_register_email.setError("Invalid Email Format") }

            if (it == ErrorName.NullPassword){ il_register_password.setError("Please Input Password Field") }
            else if(it == ErrorName.NotValidLength){ il_register_password.setError("Password Min 6 Character") }

            if (it == ErrorName.ErrorNetwork){
                DialogHandling({}).basicAlert(this@RegisterActivity, "Notification", "Network Error", "close")
            }
            if (it == ErrorName.InvalidRegister){
                DialogHandling({}).basicAlert(this@RegisterActivity, "Notification", "Unable Register, Your Email OR Phone Number has been added", "close")
            }
            if(it == ErrorName.Null){
                registerViewModel.dataRegister.observe(this, Observer { it ->
                    startActivity(Intent(this@RegisterActivity, OTPActivity::class.java))
                    intent.putExtra("cust_email", it.data.email)
                    intent.putExtra("cust_password", et_register_password.text.toString())
                    finish()
                })
            }
        })

        et_register_name?.afterTextChanged { il_register_name.setError(null) }
        et_register_address?.afterTextChanged { il_register_address.setError(null) }
        et_register_phone?.afterTextChanged { il_register_phone.setError(null) }
        et_register_email?.afterTextChanged { il_register_email.setError(null) }
        et_register_password?.afterTextChanged { il_register_password.setError(null) }

        btn_register_signup.setOnClickListener {
            val registerName = et_register_name.text.toString()
            val registerAddress = et_register_address.text.toString()
            val registerPhone = et_register_phone.text.toString()
            val registerEmail = et_register_email.text.toString()
            val registerPassword = et_register_password.text.toString()
            val registerChannel = "Mobile"

            val checkData = registerViewModel.onValidate(
                registerName,
                registerAddress,
                registerPhone,
                registerEmail,
                registerPassword,
                registerChannel
            )

        }
    }

}



