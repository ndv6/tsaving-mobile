package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity(), CoroutineScope {
    lateinit var job: Job

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        job = Job()

        tv_login_signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        btn_login_signin.setOnClickListener {
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
        }
    }
}