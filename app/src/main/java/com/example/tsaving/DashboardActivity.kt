package com.example.tsaving

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        profileImage.setOnClickListener{
//            startActivity(Intent(this@DashboardActivity, ProfileActivity::class.java))
        }

        btnSideDrawer.setOnClickListener {
//            startActivity(Intent(this@DashboardActivity, SideDrawer::class.java))
        }
    }
}
