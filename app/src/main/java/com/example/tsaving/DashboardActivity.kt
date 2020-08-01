package com.example.tsaving

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tsaving.model.DashboardResponseModel
import com.example.tsaving.model.VirtualAccount
import com.example.tsaving.vm.DashboardViewModel
import com.example.tsaving.webservice.TsavingRepository
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.nav_header_drawer.*
import java.util.*

class DashboardFragment() : androidx.fragment.app.Fragment(), LifecycleOwner {
    private var dashboardViewModel: DashboardViewModel = DashboardViewModel(TsavingRepository(), ::isLoading)
    lateinit var  dashboardAdapter : DashboardRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_dashboard, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(dashboardViewModel)

        dashboardAdapter = DashboardRecyclerViewAdapter {
            val intent = Intent(requireActivity(), VADetailsActivity::class.java)
            intent.putExtra("va_detail", it)
            startActivity(intent)
        }

        civ_dashboard.setOnClickListener{
            fragmentManager?.beginTransaction()?.replace(R.id.flContent, ProfileFragment())?.commit()
        }
        rv_dashboard_va_list.adapter = dashboardAdapter
        rv_dashboard_va_list.layoutManager = LinearLayoutManager(context)

        dashboardViewModel.apply {
            data.observe(this@DashboardFragment, androidx.lifecycle.Observer {
                tv_dashboard_name.text = it.data.custName
                tv_dashboard_email.text = it.data.custEmail
                tv_dashboard_acc_num.text = it.data.accountNum
                tv_dashboard_acc_balance.text = "Rp. ${it.data.accountBalance}"
                dashboardAdapter.vaList = it.data.virtualAccounts
                dashboardAdapter.notifyDataSetChanged()
            })
        }
    }

    fun isLoading(isFetching: Boolean) : Unit {
        if (isFetching) {
            pb_dashboard.visibility = View.GONE
            cl_dashboard.visibility = View.VISIBLE
        } else {
            pb_dashboard.visibility = View.VISIBLE
            cl_dashboard.visibility = View.GONE
        }
        return
    }
}