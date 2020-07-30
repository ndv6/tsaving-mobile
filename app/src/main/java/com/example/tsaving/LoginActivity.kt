package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.tsaving.vm.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.*
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope, LifecycleOwner {
    lateinit var job: Job
    private val loginViewModel : LoginViewModel = LoginViewModel()
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

        btn_login_signin.setOnClickListener {
            val statusLogin = loginViewModel.validateLogin(et_login_email.text.toString(), et_login_password.text.toString(), {
                    errorName: ErrorName ->
                    when (errorName){
                        ErrorName.NullEmail ->{
                            layout_login_email.setError("Please Input Email Field")
                        }
                        ErrorName.NullPassword ->{
                            layout_login_password.setError("Please Input Password Field")
                        }
                        ErrorName.InvalidEmail ->{
                            layout_login_email.setError("Invalid Email Format")
                        }
                        ErrorName.InvalidLogin ->{
                            DialogHandling().basicAlert(this@LoginActivity, "Notification", "Wrong Username / Passowrd", "Close")
                        }
                    }
            })
            if(statusLogin){
                Toast.makeText(applicationContext, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }


            var repo: TsavingRepository = TsavingRepository()

            //please change email & passwordnya from edit text then delete this comment
            var request: LoginRequestModel = LoginRequestModel("vc@gmail.com", "vcvcvc")
            Log.i("login req :", request.toString())

            lifecycleScope.launch {
                try {
                    val result = withContext(Dispatchers.IO) { repo.login(request) }
                    Log.i("result", result.toString())
                } catch (t: Throwable) {
                    when (t) {
                        is IOException -> {
                            //Please change this to handle error with Sekar's dialog box then delete this comment
                            Toast.makeText(this@LoginActivity, "Network Error", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is HttpException -> {
                            //Please change this to handle error with Sekar's dialog box then delete this comment
                            val code = t.code()
                            val errMsg = t.response().toString()
                            Log.i("login error message", t.response().toString())
                            Toast.makeText(
                                this@LoginActivity,
                                "httpError $code $errMsg",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
            // Here, please code what the app will do if API call succeed, then delete this comment
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        } //end on login on click listener
    }
}



