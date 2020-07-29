package com.example.tsaving

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_va.*

class AddVaActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_va)

        btn_addva_back.setOnClickListener{
            finish()
        }

        btn_addva_submit.setOnClickListener{
            val label = et_addva_label.text.toString()

            if (label.isEmpty()){
                Toast.makeText(applicationContext,"Please input data", Toast.LENGTH_SHORT).show()
            }
            else{

            }
        }



    }

}

