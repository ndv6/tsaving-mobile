package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.tsaving.model.VirtualAccount
import com.example.tsaving.vm.VADetailsViewModel
import kotlinx.android.synthetic.main.activity_va_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import kotlin.coroutines.CoroutineContext

class VADetailsActivity : AppCompatActivity(), LifecycleOwner, CoroutineScope {
    lateinit var job : Job
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    var vaDetailsViewModel = VADetailsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_va_details)

        lifecycle.addObserver(vaDetailsViewModel)
        job = Job()

        vaDetailsViewModel.apply {
            errorFlag.observe(this@VADetailsActivity, androidx.lifecycle.Observer {
                if (it == ErrorName.NetworkError) {
                    DialogHandling({}).basicAlert(this@VADetailsActivity, "Notification", "Network Error", "Close")
                } else if (it == ErrorName.UnableDeleteVA) {
                    DialogHandling({}).basicAlert(this@VADetailsActivity, "Notification", "Unable to delete VA", "Close")
                }
            })

            isDeleted.observe(this@VADetailsActivity, androidx.lifecycle.Observer {
                if (it == true) {
                    Toast.makeText(this@VADetailsActivity, "VA Deleted Successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@VADetailsActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            })
        }

        btn_vad_back.setOnClickListener{
            finish()
        }

        val va = intent.getParcelableExtra<VirtualAccount>("va_detail") as? VirtualAccount ?: VirtualAccount(0, "", "", 0, "", "", Date(), Date())
        tv_vad_accnumber.text = va.vaNum
        tv_vad_label.text = va.vaLabel
        tv_vad_vabalance.text = "Rp " + va.vaBalance.toString().FormatDecimal()
        layout_vad.setBackgroundColor(ContextCompat.getColor(this, colorStringToColor(va.vaColor)))

        btn_vad_edit.setOnClickListener {
            val intent = Intent(this, EditVaActivity::class.java)
            intent.putExtra("va_detail", va)
            startActivity(intent)
        }

        btn_vad_addbalance.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("va_detail", va)
            intent.putExtra("tf_type", "main-to-va")
            startActivity(intent)
        }

        btn_vad_trftomain.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("va_detail", va)
            intent.putExtra("tf_type", "va-to-main")
            startActivity(intent)
        }

        fun deleteVA() = vaDetailsViewModel.onDelete(va.vaNum?: "")
        btn_vad_delete.setOnClickListener {
            DialogHandling(::deleteVA).ApprovalDialog(
                this,
                "Confirm",
                "Are you sure deleting this virtual account?",
                "Yes",
                "No"
            )
        }

    }

    private fun colorStringToColor(color: String) : Int {
        when (color) {
            "Red" -> {
                return R.color.colorRed
            }
            "Blue" -> {
                return R.color.colorBlue
            }
            "Green" -> {
                return R.color.colorGreen
            }
            "Yellow" -> {
                return R.color.colorYellow
            }
            "Orange" -> {
                return R.color.colorOrange
            }
            "Purple" -> {
                return R.color.colorPurple
            }
        }
        return R.color.colorLight
    }
}