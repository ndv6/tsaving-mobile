package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.tsaving.vm.EditProfileViewModel
import com.example.tsaving.vm.UpdateStatus
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_edit_profile.*


class EditProfileFragment : Fragment(), LifecycleOwner {

    private var viewModel: EditProfileViewModel = EditProfileViewModel(TsavingRepository())

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

        viewModel.apply {
            data.observe(viewLifecycleOwner, Observer { newData ->
                tv_edit_profile_acc_number.text = newData.data.account_num
                et_edit_profile_name.setText(newData.data.cust_name)
                et_edit_profile_email.setText(newData.data.cust_email)
                et_edit_profile_phone.setText(newData.data.cust_phone)
                et_edit_profile_address.setText(newData.data.cust_address)
            })

            errorName.observe(viewLifecycleOwner, Observer { newErrorName ->
                et_edit_profile_name_layout.error = newErrorName
            })

            errorEmail.observe(viewLifecycleOwner, Observer { newErrorEmail ->
                et_edit_profile_email_layout.error = newErrorEmail
            })

            errorPhone.observe(viewLifecycleOwner, Observer { newErrorPhone ->
                et_edit_profile_phone_layout.error = newErrorPhone
            })

            errorAddress.observe(viewLifecycleOwner, Observer { newErrorAddress ->
                et_edit_profile_address_layout.error = newErrorAddress
            })

            errorNetwork.observe(viewLifecycleOwner, Observer { newErrorNetwork ->
                activity?.let {
                    DialogHandling {}.basicAlert(it, "Update Failed", newErrorNetwork, "close")
                }
            })

            updateStatus.observe(viewLifecycleOwner, Observer { newUpdateStatus ->
                if (newUpdateStatus == UpdateStatus.SUCCESS) {
                    activity?.let {
                        DialogHandling {
                            fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())
                                ?.commit()
                        }.basicAlert(it, "Update Success", "Profile updated", "close")
                    }
                } else if (newUpdateStatus == UpdateStatus.EMAIL_CHANGED) {
                    activity?.let {
                        val intent = Intent(activity, OTPActivity::class.java)
                        intent.putExtra("cust_email", et_edit_profile_email.text.toString())
                        startActivity(intent)
                        activity!!.finish()
                    }
                }
            })

            loadingPage.observe(viewLifecycleOwner, Observer { isLoadingPage ->
                if (isLoadingPage) {
                    pb_edit_profile_page.visibility = ProgressBar.VISIBLE
                } else {
                    pb_edit_profile_page.visibility = ProgressBar.GONE
                }
            })

            loadingButton.observe(viewLifecycleOwner, Observer { isLoadingButton ->
                if (isLoadingButton) {
                    pb_edit_profile_button.visibility = ProgressBar.VISIBLE
                } else {
                    pb_edit_profile_button.visibility = ProgressBar.GONE
                }
            })
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

            if (isValid) {
                viewModel.updateData(inputName, inputEmail, inputPhone, inputAddress)
            }

        }
    }
}