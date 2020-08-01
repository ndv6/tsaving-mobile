package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tsaving.vm.EditVaViewModel
import com.example.tsaving.webservice.TsavingRepository
import com.google.gson.internal.bind.ArrayTypeAdapter
import kotlinx.android.synthetic.main.activity_va_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class EditVaActivity: AppCompatActivity(), CoroutineScope {
    lateinit var job: Job

//    var vaNum = intent.getStringExtra("va_num")
    val vaNum = "2007301876002"
    val vaLabel = "Tabungan Rumah"
    val vaColor = "Red"

    val editVaViewModel : EditVaViewModel = EditVaViewModel(vaNum)

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_edit)

        tv_vae_num.text = vaNum
        et_vae_label.setText(vaLabel)

        val spinner: Spinner = findViewById(R.id.sp_vae_color)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.color_va,
            android.R.layout.simple_spinner_item
        )

//        spinner.setSelection(adapter.)


//        sp_vae_color.selectedItem
//
//

        btn_vae_save.setOnClickListener {
            val label = et_vae_label.text.toString()
//            val color = sp_vae_color.selectedItem.toString()
            val status = editVaViewModel.validateEditVa(label)
            if (!status) {
                Toast.makeText(applicationContext, "Please input data", Toast.LENGTH_SHORT).show()
            } else {
                editVaViewModel.fetchVaEditData()
//                startActivity(Intent(this, VADetailsActivity::class.java))
            }
        }
    }
}