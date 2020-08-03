package com.example.tsaving

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tsaving.model.VirtualAccount
import com.example.tsaving.vm.DashboardViewModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.util.ArrayList

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

        iv_dashboard_search.setOnClickListener{
            val dbSearchFragment = DashboardSearchFragment()
            val bundle = Bundle()
            bundle.putParcelableArrayList("va_list", dashboardAdapter.vaList as ArrayList<VirtualAccount>)
            dbSearchFragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.flContent, dbSearchFragment, "dashboard_search")?.commit()
        }

        iv_dashboard_sort.setOnClickListener{
            dashboardAdapter.vaList = dashboardAdapter.vaList.reversed()
            dashboardAdapter.notifyDataSetChanged()
        }

//        api listener
        dashboardViewModel.apply {
            data.observe(this@DashboardFragment, androidx.lifecycle.Observer {
                BaseApplication.accNumber = it.data.accountNum
                tv_dashboard_name.text = it.data.custName
                tv_dashboard_email.text = it.data.custEmail
                tv_dashboard_acc_num.text = it.data.accountNum
                tv_dashboard_acc_balance.text = "Rp. ${it.data.accountBalance.toString().FormatDecimal()}"
                dashboardAdapter.vaList = it.data.virtualAccounts
                dashboardAdapter.notifyDataSetChanged()
            })
            errMessage.observe(this@DashboardFragment, Observer {
                DialogHandling(::fetchApi).basicAlert(view.context, "Dashboard can not be loaded", it, "Reload")
            })
        }
    }

    private fun fetchApi() = dashboardViewModel.fetchDashboardData()

    private fun isLoading(isFetching: Boolean) : Unit {
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