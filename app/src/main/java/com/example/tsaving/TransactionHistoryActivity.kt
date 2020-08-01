package com.example.tsaving

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.tsaving.view_model.TransactionHistoryViewModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.transaction_history.*

class TransactionHistoryFragment : androidx.fragment.app.Fragment(), LifecycleOwner {
    private val transactionHistoryAdapter = TransactionHistoryAdapter()
    private val transactionHistoryViewModel: TransactionHistoryViewModel =
        TransactionHistoryViewModel(TsavingRepository())
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.transaction_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefreshLayout = view.findViewById(R.id.srl_transaction_history)
        lifecycle.addObserver(transactionHistoryViewModel)

        rv_transaction_history.adapter = transactionHistoryAdapter
        rv_transaction_history.layoutManager = LinearLayoutManager(context)

        transactionHistoryViewModel.apply {
            dataWrapper.observe(this@TransactionHistoryFragment, Observer {
                transactionHistoryAdapter.transactionHistoryList = it.data
                transactionHistoryAdapter.notifyDataSetChanged()
            })
        }

        //ScrollListener
        //vivsible item = total/vis. vis 50% total, call

        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            //API Call, Page++ here

            val handler = Handler()
            handler.postDelayed(Runnable {
                if (swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }, 1000)

        })



        //rv_transaction_history.addOnScrollListener()
    }
}