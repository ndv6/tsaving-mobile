package com.example.tsaving

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        civ_dashboard.setOnClickListener{
//            startActivity(Intent(this@DashboardActivity, ProfileActivity::class.java))
        }

        btn_dashboard_drawer.setOnClickListener {
//            startActivity(Intent(this@DashboardActivity, SideDrawer::class.java))
        }
    }
}
