package com.example.tsaving

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.LifecycleOwner
import com.example.tsaving.vm.AddVaViewModel
import kotlinx.android.synthetic.main.activity_add_va.*


class AddVaFragment: androidx.fragment.app.Fragment(),LifecycleOwner {
    private val addVaViewModel: AddVaViewModel = AddVaViewModel()

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
//        lifecycle.addObserver(addVaViewModel)

        addVaViewModel.errorLabel.observe(this, Observer{
                newErrorName -> layout_addva_label.setError(newErrorName)
        })

        addVaViewModel._data.observe(this, Observer{
            Log.i("_data observe", it.toString())
        })

        et_addva_label?.afterTextChanged {
            layout_addva_label.setError(null)
        }
        btn_addva_submit.setOnClickListener {
            val label = et_addva_label.text.toString()
            addVaViewModel.validateAddVa(label)
            if(layout_addva_label.error == null) {
//                fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())
//                    ?.commit()
                addVaViewModel.addVaRequest()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
