package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_va_details.*

class VADetailsActivity : AppCompatActivity() {
    private val vaViewModel: VADetailsViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(VADetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_details)

        btn_vad_addbalance.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }

        btn_vad_trftomain.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            startActivity(intent)
        }

        btn_vad_back.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        vaViewModel.onCreate()
        vaViewModel.virtualAccount.observe(this, Observer {
//            ngeset textview nya jadi value data view model
            var textExample = it
        })
    }
}
