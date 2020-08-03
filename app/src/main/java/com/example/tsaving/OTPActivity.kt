package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.tsaving.vm.OTPViewModel
import kotlinx.android.synthetic.main.verify_email.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class OTPActivity : AppCompatActivity(), LifecycleOwner, CoroutineScope {

    lateinit var job : Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    var otpViewModel = OTPViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verify_email)

        lifecycle.addObserver(otpViewModel)
        job = Job()
        val cust_email = intent.getStringExtra("cust_email")?: ""
        val cust_password = intent.getStringExtra("cust_password")?: ""

        var page = intent.getStringExtra("page")
        if (page == "login") {
            tv_verify_login.visibility = View.VISIBLE
        }

        otpViewModel.apply {
            _error.observe(this@OTPActivity, Observer {
                if (it == ErrorName.NullOTP) {
                    layout_otp_token.setError("OTP can not be blank")
                } else if (it == ErrorName.NetworkError) {
                    DialogHandling({}).basicAlert(this@OTPActivity, "Notification", "Network Error", "close")
                } else if (it == ErrorName.FailedToRecognizeOTP) {
                    DialogHandling({}).basicAlert(this@OTPActivity, "Notification", "Failed to recognize OTP", "close")
                } else if (it == ErrorName.LoginUnAuthorized) {
                    startActivity(Intent(this@OTPActivity, OTPActivity::class.java))
                }
            })

            isValid.observe(this@OTPActivity, Observer {
                if (it == true) {
                    login(cust_email, cust_password)
                }
            })

            dataLogin.observe(this@OTPActivity, Observer {
                if(it.status == "SUCCESS" && statusLogin.value == false){
                    BaseApplication.token = it.data.token
                    BaseApplication.custEmail = it.data.cust_email
                    BaseApplication.custName = it.data.cust_name
                    val intent = Intent(this@OTPActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            })

            statusPB.observe(this@OTPActivity, Observer {
                if(it == true){
                    isLoadingLogin(true)
                }
                else{
                    isLoadingLogin(false)
                }

            })

            dataToken.observe(this@OTPActivity, Observer {
                if (it.status == "SUCCESS") {
                    resendOTP(cust_email, it.data.token)
                }
            })
        }

        et_otp_token.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length < 0 ) {
                    layout_otp_token.setError("OTP can not be blank")
                } else {
                    layout_otp_token.setError(null)
                }
            }
        })

        tv_otp_resendOTP.setOnClickListener {
            otpViewModel.getOTP(cust_email)
        }

        btn_otp_send.setOnClickListener {
            otpViewModel.onValidate(et_otp_token.text.toString(), cust_email)
        }
    }

    fun isLoadingLogin(isFetching: Boolean) {
        if (isFetching) {
            layout_otp_token.visibility = View.GONE
            pb_otp.visibility = View.VISIBLE
        } else {
            layout_otp_token.visibility = View.VISIBLE
            pb_otp.visibility = View.GONE
        }
    }
}