package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.tsaving.vm.AddVaViewModel
import kotlinx.android.synthetic.main.activity_add_va.*

class AddVaActivity: AppCompatActivity(), LifecycleOwner {

    private val addVaViewModel : AddVaViewModel = AddVaViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_va)
//        lifecycle.addObserver(addVaViewModel)

        btn_addva_submit.setOnClickListener{
            val label = et_addva_label.text.toString()
            val status = addVaViewModel.validateAddVa(label)
            if(!status){
                Toast.makeText(applicationContext,"Please input data", Toast.LENGTH_SHORT).show()
            }
            else{
                startActivity(Intent(this,VADetailsActivity::class.java))
            }
        }



    }

}

