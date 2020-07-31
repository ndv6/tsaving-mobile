package com.example.tsaving

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.tsaving.vm.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.verify_email.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope, LifecycleOwner {
    lateinit var job: Job
    private val loginViewModel = LoginViewModel()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        lifecycle.addObserver(loginViewModel)
        job = Job()

        //setting default event
        et_login_email?.afterTextChanged { layout_login_email.setError(null) }
        et_login_password?.afterTextChanged { layout_login_password.setError(null) }
        tv_login_signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        loginViewModel.apply {
            flagStatus.observe(this@LoginActivity, Observer {
                if(flagStatus.value == ErrorName.NullEmailAndPass){
                    layout_login_email.setError("Please Input Email Field")
                    layout_login_password.setError("Please Input Password Field")
                }
                else if(flagStatus.value == ErrorName.NullEmail){
                    layout_login_email.setError("Please Input Email Field")
                }
                else if(flagStatus.value == ErrorName.NullPassword){
                    layout_login_password.setError("Please Input Password Field")
                }
                else if(flagStatus.value == ErrorName.InvalidEmail){
                    layout_login_email.setError("Invalid Email Format")
                }
                else if(flagStatus.value == ErrorName.ErrorNetwork){
                    DialogHandling().basicAlert(this@LoginActivity, "Notification", "Network Error", "close")
                }
                else if(flagStatus.value == ErrorName.InvalidLogin){
                    DialogHandling().basicAlert(this@LoginActivity, "Notification", "Wrong Username Or Password", "close")
                }
            })
            dataLogin.observe(this@LoginActivity, Observer {
                if(it.status == "SUCCESS"){
                    //save token
                    BaseApplication.token = it.data.token
                    finish()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()

                }
            })
        }

        btn_login_signin.setOnClickListener {
            loginViewModel.validateLogin(et_login_email.text.toString(), et_login_password.text.toString())
        }
    } //end override oncreate
}




