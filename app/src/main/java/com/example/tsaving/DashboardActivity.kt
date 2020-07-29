package com.example.tsaving

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tsaving.model.VirtualAccount
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.*

class DashboardFragment : androidx.fragment.app.Fragment() {
    val dashboardAdapter = DashboardRecyclerViewAdapter()
    val dummyVa = VirtualAccount(1, "23412343", "32472984729", 50000, "Blue", "House", Calendar.getInstance().time, Calendar.getInstance().time)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        civ_dashboard.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())?.commit()
        }

        rv_dashboard_va_list.adapter = dashboardAdapter
        rv_dashboard_va_list.layoutManager = LinearLayoutManager(context)

        dashboardAdapter.vaList.add(dummyVa)

        super.onViewCreated(view, savedInstanceState)
    }
}