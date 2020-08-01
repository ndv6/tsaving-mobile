package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.tsaving.vm.OTPViewModel
import kotlinx.android.synthetic.main.activity_login.*
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

        otpViewModel.apply {
            _error.observe(this@OTPActivity, Observer {
                if (it == ErrorName.NullOTP) {
                    layout_otp_token.setError("OTP can not be blank")
                } else if (it == ErrorName.NetworkError) {
                    layout_otp_token.setError("Network Error")
                } else if (it == ErrorName.FailedToRecognizeOTP) {
                    layout_otp_token.setError("Failed to recognize OTP")
                }
            })

            isValid.observe(this@OTPActivity, Observer {
                if (isValid.value == true) {
                    startActivity(Intent(this@OTPActivity, MainActivity::class.java))
                    finish()
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

        btn_otp_send.setOnClickListener {
            val cust_email = intent.getStringExtra("cust_email").toString()
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