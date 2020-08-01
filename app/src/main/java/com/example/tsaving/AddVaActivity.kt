package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
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

        addVaViewModel.errorLabel.observe(this, Observer{
                newErrorName -> layout_addva_label.setError(newErrorName)
        })
        addVaViewModel.status.observe(this, Observer { it ->
            if(it == true){
//                ErrorDialogHandling( requireContext(),"Bisa Nih di Update", "Yes").errorResponseDialog()
//                    cl_addva.visibility = View.GONE
//                    pb_addva.visibility = View.VISIBLE
                    startActivity(Intent(this@AddVaFragment.context,MainActivity::class.java))
            }else{
                ErrorDialogHandling(requireContext(), "An Error has occured.", "No").errorResponseDialog()
                DialogHandling({}).basicAlert(requireContext(), "Notification", "Network Error", "close")
            }
        })

        et_addva_label?.afterTextChanged {
            layout_addva_label.setError(null)
        }
        btn_addva_submit.setOnClickListener {
            val color = sp_addva_color.selectedItem.toString()
            val label = et_addva_label.text.toString()
            addVaViewModel.validateAddVa(label,color)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}
