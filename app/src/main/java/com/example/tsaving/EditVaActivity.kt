package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.tsaving.vm.EditVaViewModel
import kotlinx.android.synthetic.main.activity_va_edit.*

class EditVaActivity: AppCompatActivity(), LifecycleOwner {

    private val editVaViewModel : EditVaViewModel = EditVaViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_edit)
        lifecycle.addObserver(editVaViewModel)

        btn_vae_save.setOnClickListener {
            val label = et_vae_label.text.toString()
            val status = editVaViewModel.validateEditVa(label)
            if (!status) {
                Toast.makeText(applicationContext, "Please input data", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this, VADetailsActivity::class.java))
            }
        }
    }
}