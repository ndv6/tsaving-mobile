package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
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

        otpViewModel.apply {
            _error.observe(this@OTPActivity, Observer { layout_otp_token })
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
            otpViewModel.onValidate(et_otp_token.text.toString())
            val valid = otpViewModel.isValid
            val errorMsg = otpViewModel._error.value
            if (valid.value == false) {
                layout_otp_token.setError(errorMsg)
            } else {
                layout_otp_token.setError(null)
                startActivity(Intent(this@OTPActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}