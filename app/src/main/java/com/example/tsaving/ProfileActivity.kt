package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.tsaving.vm.ProfileViewModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileFragment : Fragment(), LifecycleOwner {

    private val viewModel : ProfileViewModel = ProfileViewModel(TsavingRepository())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)

        btn_profile_edit.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.flContent, EditProfileFragment())
                ?.commit()
        }

        btn_profile_change_password.setOnClickListener {
            startActivity(Intent(activity, UpdatePasswordActivity::class.java))
        }

        viewModel.data.observe(viewLifecycleOwner, Observer { newData ->
            tv_profile_acc_num.text = newData.data.account_num
            tv_profile_name.text = newData.data.cust_name
            tv_profile_email.text = newData.data.cust_email
            tv_profile_phone.text = newData.data.cust_phone
            tv_profile_address.text = newData.data.cust_address

            if (newData.data.is_verified) {
                tv_profile_verified.text = "Verified"
                tv_profile_verified.setTextColor(resources.getColor(R.color.colorAccent))
            } else {
                tv_profile_verified.text = "Not Verified"
                tv_profile_verified.setTextColor(resources.getColor(R.color.colorPrimary))
            }
        })
    }
}
