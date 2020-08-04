package com.example.tsaving

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
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

        lifecycle.addObserver(registerViewModel)

        registerViewModel.apply {
            flagStatus.observe(this@RegisterActivity, Observer { it ->
                if (it == ErrorName.NullName) {
                    il_register_name.setError("Please Input Name Field")
                }

                if (it == ErrorName.NullAddress) {
                    il_register_address.setError("Please Input Address Field")
                }
                if (it == ErrorName.NullPhone) {
                    il_register_phone.setError("Please Input Phone Field")
                }

                if (it == ErrorName.NullEmail) {
                    il_register_email.setError("Please Input Email Field")
                } else if (it == ErrorName.InvalidEmail) {
                    il_register_email.setError("Invalid Email Format")
                }

                if (it == ErrorName.NullPassword) {
                    il_register_password.setError("Please Input Password Field")
                } else if (it == ErrorName.NotValidLength) {
                    il_register_password.setError("Password Min 6 Character")
                }

                if (it == ErrorName.NullConfirmPassword) {
                    il_register_password_confirm.setError("Please Input Confirm Password Field")
                } else if (it == ErrorName.NotValidLengthConfirm) {
                    il_register_password_confirm.setError("Confirm Password Min 6 Character")
                } else if (it == ErrorName.InvalidConfirmPassword){
                    il_register_password_confirm.setError("Your Confirm Password Not Match")
                }

                if (it == ErrorName.ErrorNetwork) {
                    DialogHandling({}).basicAlert(
                        this@RegisterActivity,
                        "Notification",
                        "Network Error",
                        "close"
                    )
                }
                if (it == ErrorName.InvalidRegister) {
                    DialogHandling({}).basicAlert(
                        this@RegisterActivity,
                        "Notification",
                        "Unable Register, Your Email OR Phone Number has been added",
                        "close"
                    )
                }
            })

            dataRegister.observe(this@RegisterActivity, Observer { it ->
                if (it.status == "SUCCESS") {
                    val intent = Intent(this@RegisterActivity, OTPActivity::class.java)
                    intent.putExtra("cust_email", et_register_email.text.toString())
                    intent.putExtra("cust_password", et_register_password.text.toString())
                    startActivity(intent)
                    finish()
                }
            })

            progresBar.observe(this@RegisterActivity, Observer {
                if (it == true){
                    pb_register.visibility = View.VISIBLE
                    btn_register_signup.isEnabled = false
                    btn_register_signup.setBackgroundResource(R.drawable.button_rounded_grey)
                }
                else{
                    Handler().postDelayed({
                        pb_register.visibility = View.GONE
                        btn_register_signup.isEnabled = true
                        btn_register_signup.setBackgroundResource(R.drawable.button_rounded)
                    }, 2000)
                }
            })
        }

        et_register_name?.afterTextChanged { il_register_name.setError(null)}
        et_register_address?.afterTextChanged { il_register_address.setError(null)}
        et_register_phone?.afterTextChanged { il_register_phone.setError(null)}
        et_register_email?.afterTextChanged { il_register_email.setError(null)}
        et_register_password?.afterTextChanged { il_register_password.setError(null)}
        et_register_password_confirm?.afterTextChanged { il_register_password_confirm.setError(null)}

        btn_register_signup.setOnClickListener {

            val registerName = et_register_name.text.toString()
            val registerAddress = et_register_address.text.toString()
            val registerPhone = et_register_phone.text.toString()
            val registerEmail = et_register_email.text.toString()
            val registerPassword = et_register_password.text.toString()
            val registerConfirmPassword = et_register_password_confirm.text.toString()
            val registerChannel = "Mobile"

            val checkData = registerViewModel.onValidate(
                registerName,
                registerAddress,
                registerPhone,
                registerEmail,
                registerPassword,
                registerConfirmPassword,
                registerChannel
            )
        }
    }

}



