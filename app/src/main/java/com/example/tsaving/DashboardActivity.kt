package com.example.tsaving

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tsaving.model.ResponseModel
import com.example.tsaving.model.VirtualAccount
import com.example.tsaving.vm.DashboardViewModel
import com.example.tsaving.webservice.TsavingRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.util.*
import kotlin.coroutines.CoroutineContext

class DashboardFragment() : androidx.fragment.app.Fragment(), CoroutineScope, LifecycleOwner {
    var job: Job = Job()
    override val coroutineContext: CoroutineContext get() = job + Dispatchers.Main
    private var dashboardViewModel: DashboardViewModel = DashboardViewModel(TsavingRepository())
    val dashboardAdapter = DashboardRecyclerViewAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lifecycle.addObserver(dashboardViewModel)
        return inflater.inflate(R.layout.activity_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dashboardViewModel.apply {
            data.observe(this@DashboardFragment, androidx.lifecycle.Observer {
                tv_dashboard_name.text = it.data.cust_name
                tv_dashboard_email.text = it.data.cust_email
                tv_dashboard_acc_num.text = it.data.account_num
                tv_dashboard_acc_balance.text = it.data.account_balance.toString()
                dashboardAdapter.vaList = it.data.virtual_accounts
            })
        }

        civ_dashboard.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())?.commit()
        }
        rv_dashboard_va_list.adapter = dashboardAdapter
        rv_dashboard_va_list.layoutManager = LinearLayoutManager(context)

        super.onViewCreated(view, savedInstanceState)
    }
}