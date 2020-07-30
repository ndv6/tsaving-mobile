package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.tsaving.model.request.LoginRequestModel
import com.example.tsaving.vm.ProfileViewModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ProfileFragment : Fragment(), LifecycleOwner {

    private val viewModel = ProfileViewModel()

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

        tv_profile_acc_num.text = viewModel.accNum
        tv_profile_name.text = viewModel.name.value
        tv_profile_email.text = viewModel.email.value
        tv_profile_phone.text = viewModel.phone.value
        tv_profile_address.text = viewModel.address.value

        if (viewModel.isVerified.value == true) {
            tv_profile_verified.text = "Verified"
            tv_profile_verified.setTextColor(resources.getColor(R.color.colorAccent))
        } else {
            tv_profile_verified.text = "Not Verified"
            tv_profile_verified.setTextColor(resources.getColor(R.color.colorPrimary))
        }
    }
}
