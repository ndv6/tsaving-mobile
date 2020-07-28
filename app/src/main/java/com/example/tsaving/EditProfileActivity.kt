package com.example.tsaving


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileFragment: androidx.fragment.app.Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_edit_profile_save.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())?.commit()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}