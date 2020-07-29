package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.zip.Inflater

class ProfileFragment: androidx.fragment.app.Fragment () {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_profile_edit.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.flContent, EditProfileFragment())?.commit()
        }

        btn_profile_change_password.setOnClickListener {
            startActivity(Intent(activity, UpdatePasswordActivity::class.java))
        }

        super.onViewCreated(view, savedInstanceState)
    }
}
