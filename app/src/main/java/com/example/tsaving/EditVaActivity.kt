package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.tsaving.model.VirtualAccount
import com.example.tsaving.vm.EditVaViewModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_va_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import kotlin.coroutines.CoroutineContext

class EditVaActivity: AppCompatActivity(), CoroutineScope, LifecycleOwner {
    lateinit var job: Job

//    var vaNum = intent.getStringExtra("va_num")
    val vaNum = "2007301876003"
    var vaLabel = "Tabungan Rumah"
    var vaColor = "Yellow"

    val editVaViewModel : EditVaViewModel = EditVaViewModel(TsavingRepository(), vaNum)

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_edit)

        val va = intent.getParcelableExtra<VirtualAccount>("va_detail") as? VirtualAccount ?: VirtualAccount(0, "", "", 0, "", "", Date(), Date())

        tv_vae_num.text = vaNum
        et_vae_label.setText(vaLabel)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.color_va,
            android.R.layout.simple_spinner_item
        )

        sp_vae_color.setSelection(adapter.getPosition(vaColor))


        et_vae_label?.afterTextChanged { til_vae_label.setError(null) }

        btn_vae_save.setOnClickListener {
            vaLabel = et_vae_label.text.toString()
            vaColor = sp_vae_color.selectedItem.toString()
            val status = editVaViewModel.validateEditVa(vaLabel)
            if (!status) {
                til_vae_label.setError("Please Input New Label")
            } else {
                editVaViewModel.fetchVaEditData(vaLabel, vaColor)
                startActivity(Intent(this, VADetailsActivity::class.java))
            }
        }

        statusPB.observe(this, Observer {
            if(it == true){
                isLoadingLogin(true)
            }
            else{
                isLoadingLogin(false)
            }

        })
    }

    fun isLoadingEditVa(isFetching: Boolean) {
        if (isFetching) {
            cl_vae.visibility = View.GONE
            pb_vae.visibility = View.VISIBLE
        } else {
            cl_vae.visibility = View.VISIBLE
            pb_vae.visibility = View.GONE
        }
    }
}