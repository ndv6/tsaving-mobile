package com.example.tsaving

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tsaving.vm.EditProfileViewModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.btn_edit_profile_save
import kotlinx.android.synthetic.main.activity_edit_profile.et_edit_profile_address
import kotlinx.android.synthetic.main.activity_edit_profile.et_edit_profile_email
import kotlinx.android.synthetic.main.activity_edit_profile.et_edit_profile_name
import kotlinx.android.synthetic.main.activity_edit_profile.et_edit_profile_phone
import kotlinx.android.synthetic.main.activity_profile.*


class EditProfileFragment : Fragment(), LifecycleOwner {

    private var viewModel : EditProfileViewModel =  EditProfileViewModel(TsavingRepository())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)

        viewModel.data.observe(viewLifecycleOwner, Observer { newData ->
            tv_edit_profile_acc_number.text = newData.data.account_num
            et_edit_profile_name.setText(newData.data.cust_name)
            et_edit_profile_email.setText(newData.data.cust_email)
            et_edit_profile_phone.setText(newData.data.cust_phone)
            et_edit_profile_address.setText(newData.data.cust_address)
        })

        viewModel.errorName.observe(viewLifecycleOwner, Observer { newErrorName ->
            et_edit_profile_name_layout.error = newErrorName
        })

        viewModel.errorEmail.observe(viewLifecycleOwner, Observer { newErrorEmail ->
            et_edit_profile_email_layout.error = newErrorEmail
        })

        viewModel.errorPhone.observe(viewLifecycleOwner, Observer { newErrorPhone ->
            et_edit_profile_phone_layout.error = newErrorPhone
        })

        viewModel.errorAddress.observe(viewLifecycleOwner, Observer { newErrorAddress ->
            et_edit_profile_address_layout.error = newErrorAddress
        })

        viewModel.loadingPage.observe(viewLifecycleOwner, Observer { isLoadingPage ->
            if (isLoadingPage) {
                pb_edit_profile_page.visibility = ProgressBar.VISIBLE
            } else {
                pb_edit_profile_page.visibility = ProgressBar.GONE
            }
        })

        viewModel.loadingButton.observe(viewLifecycleOwner, Observer { isLoadingButton ->
            if (isLoadingButton) {
                pb_edit_profile_button.visibility = ProgressBar.VISIBLE
            } else {
                pb_edit_profile_button.visibility = ProgressBar.GONE
            }
        })

        btn_edit_profile_save.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())
                ?.commit()
        }

        et_edit_profile_name?.afterTextChanged { et_edit_profile_name_layout.error = null }
        et_edit_profile_email?.afterTextChanged { et_edit_profile_email_layout.error = null }
        et_edit_profile_phone?.afterTextChanged { et_edit_profile_phone_layout.error = null }
        et_edit_profile_address?.afterTextChanged { et_edit_profile_address_layout.error = null }

        btn_edit_profile_save.setOnClickListener {
            val inputName = et_edit_profile_name.text.toString()
            val inputEmail = et_edit_profile_email.text.toString()
            val inputPhone = et_edit_profile_phone.text.toString()
            val inputAddress = et_edit_profile_address.text.toString()

            val isValid = viewModel.checkValidity(inputName, inputEmail, inputPhone, inputAddress)

//            if (isValid) {
//                updateProfile()
//            }

        }
    }

    private fun updateProfile() {
        Toast.makeText(activity, "LGTM", Toast.LENGTH_LONG).show()
        fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())?.commit()
    }
}