package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import com.example.tsaving.model.VirtualAccount
import com.example.tsaving.vm.EditVaViewModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_va_details.*
import kotlinx.android.synthetic.main.activity_va_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import kotlin.coroutines.CoroutineContext

class EditVaActivity: AppCompatActivity(), CoroutineScope, LifecycleOwner {
    lateinit var job: Job

    var vaNum = String()

    val editVaViewModel : EditVaViewModel = EditVaViewModel(TsavingRepository(), vaNum)

    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_edit)
        lifecycle.addObserver(editVaViewModel)

        val va = intent.getParcelableExtra<VirtualAccount>("va_detail") as? VirtualAccount ?: VirtualAccount(0, "", "", 0, "", "", Date(), Date())
        val adapter = ArrayAdapter.createFromResource(
            this@EditVaActivity,
            R.array.color_va,
            android.R.layout.simple_spinner_item
        )

        btn_vae_back.setOnClickListener{
            finish()
        }

        btn_vae_cancel.setOnClickListener{
            finish()
        }

        editVaViewModel.vaNum = va.vaNum.toString()

        tv_vae_num.text = va.vaNum
        et_vae_label.setText(va.vaLabel)
        sp_vae_color.setSelection(adapter.getPosition(va.vaColor))

        et_vae_label?.afterTextChanged { til_vae_label.setError(null) }

        btn_vae_save.setOnClickListener {
            val newVaLabel = et_vae_label.text.toString()
            val newVaColor = sp_vae_color.selectedItem.toString()
            val status = editVaViewModel.validateEditVa(newVaLabel)
            if (!status) {
                til_vae_label.setError("Please Input New Label")
            } else {
                editVaViewModel.fetchVaEditData(newVaLabel, newVaColor)
            }
        }

        editVaViewModel.apply {
            flagStatus.observe(this@EditVaActivity, androidx.lifecycle.Observer {
                if(it == false){
                    DialogHandling({}).basicAlert(this@EditVaActivity, "Notification", "Network Error", "close")
                }
            })
            data.observe(this@EditVaActivity, androidx.lifecycle.Observer {
                if (it.status == "SUCCESS"){
                    val intent = Intent(this@EditVaActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
            })
            statusPB.observe(this@EditVaActivity, androidx.lifecycle.Observer {
                if(it == true){
                    isLoadingEditVa(true)
                }
                else{
                    isLoadingEditVa(false)
                }
            })
        }
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