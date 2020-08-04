package com.example.tsaving

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tsaving.vm.DashboardViewModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Collections.replaceAll

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

        rv_dashboard_va_list.adapter = dashboardAdapter
        rv_dashboard_va_list.layoutManager = LinearLayoutManager(context)

//        api listener
        dashboardViewModel.apply {
            data.observe(this@DashboardFragment, androidx.lifecycle.Observer {
                var cardNumber = it.data.cardNumber
                val num1 = cardNumber.substring(0,4)
                val num2 = cardNumber.substring(4,8)
                val num3 = cardNumber.substring(8,12)
                val cardNum = "$num1 $num2 $num3"

                val month = it.data.expired.substring(5,7)
                val year = it.data.expired.substring(2,4)

                val date = "$month/$year"

                BaseApplication.accNumber = it.data.accountNum
                tv_dashboard_name.text = it.data.custName
                tv_dashboard_email.text = it.data.custEmail
                tv_dashboard_acc_num.text = it.data.accountNum
                tv_dashboard_static_card_number.text = cardNum
                tv_dashboard_static_card_exp.text = date

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