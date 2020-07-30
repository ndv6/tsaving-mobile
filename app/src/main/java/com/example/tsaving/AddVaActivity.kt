package com.example.tsaving

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//            fragmentManager?.beginTransaction()?.replace(R.id.flContent, DashboardFragment())?.commit()
//        }
        val label = et_addva_label.text.toString()


        btn_addva_submit.setOnClickListener{


            val status = addVaViewModel.validateAddVa(label)
            if(!status){
                layout_addva_label.setError("Please fill this field")
            }
            else{
                fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())?.commit()

            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

}

