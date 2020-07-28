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

//class ProfileActivity : Activity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_profile)
//
//        btn_profile_edit.setOnClickListener {
//            val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java)
//            startActivity(intent)
//        }
//    }
//}

class ProfileFragment: androidx.fragment.app.Fragment () {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }
}
