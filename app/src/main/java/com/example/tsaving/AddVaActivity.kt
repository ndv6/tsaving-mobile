package com.example.tsaving

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_va.*

class AddVaActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_va)

        button_addva_back.setOnClickListener{
            finish()
        }

        button_addva_submit.setOnClickListener{
            val label = label_addva_label.text.toString()
//            val color = spinner_color.

            if (label.isEmpty()){
                Toast.makeText(applicationContext,"Please input data", Toast.LENGTH_SHORT).show()
            }
            else{

            }
        }



    }

}

