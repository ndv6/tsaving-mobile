package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_va_edit.*

class EditVaActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_edit)

        btn_vae_save.setOnClickListener {
            startActivity(Intent(this, VADetailsActivity::class.java))
        }
    }
}