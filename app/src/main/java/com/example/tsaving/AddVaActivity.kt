package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tsaving.vm.AddVaViewModel
import kotlinx.android.synthetic.main.activity_add_va.*

class AddVaFragment: androidx.fragment.app.Fragment() {
    private val addVaViewModel : AddVaViewModel = AddVaViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_add_va, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        btn_addva_back.setOnClickListener{
////            fragmentManager?.beginTransaction()?.replace(R.id.flContent, DashboardFragment())?.commit()
////        }

        btn_addva_submit.setOnClickListener{
            val label = et_addva_label.text.toString()
            val status = addVaViewModel.validateAddVa(label)
            if(!status){
                Toast.makeText(context,"Please input data", Toast.LENGTH_SHORT).show()
            }
            else{
                fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())?.commit()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

}

