package com.example.tsaving

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tsaving.model.PaginationScrollListener
import com.example.tsaving.vm.TransactionHistoryViewModel
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.android.synthetic.main.transaction_history.*

class TransactionHistoryFragment : androidx.fragment.app.Fragment(), LifecycleOwner {
    private val transactionHistoryAdapter = TransactionHistoryAdapter()
    private val transactionHistoryViewModel: TransactionHistoryViewModel =
        TransactionHistoryViewModel(TsavingRepository())
    private var isLastPage: Boolean = false

    companion object var firstPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.transaction_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(transactionHistoryViewModel)

        rv_transaction_history.adapter = transactionHistoryAdapter
        rv_transaction_history.layoutManager = LinearLayoutManager(context)

        transactionHistoryViewModel.listTransactionHistory(firstPage)

        transactionHistoryViewModel.apply {
            transactionHistoryMutableLiveData.observe(this@TransactionHistoryFragment, Observer {
                transactionHistoryAdapter.transactionHistoryList = it
                transactionHistoryAdapter.notifyDataSetChanged()
            })

            errorMutableLiveData.observe(this@TransactionHistoryFragment, Observer {
                DialogHandling({}).basicAlert(
                    view.context,
                    it.errorName.toString(),
                    it.errorMessage,
                    "Close"
                )
            })
        }

        rv_transaction_history?.addOnScrollListener(object : PaginationScrollListener(
            rv_transaction_history.layoutManager as LinearLayoutManager
        ) {
            override fun loadItems(currentPage: Int, recyclerView: RecyclerView) {
                transactionHistoryViewModel.apply {
                    if (!isLastPage) {
                        transactionHistoryViewModel.listTransactionHistory(currentPage)
                        transactionHistoryMutableLiveData.observe(
                            this@TransactionHistoryFragment,
                            Observer {
                                transactionHistoryAdapter.transactionHistoryList = it
                                transactionHistoryAdapter.notifyDataSetChanged()
                            })
                    }

                    errorMutableLiveData.observe(this@TransactionHistoryFragment, Observer {
                        DialogHandling({}).basicAlert(
                            view.context,
                            it.errorName,
                            it.errorMessage,
                            "Close"
                        )
                    })

                    isLastPageMutableLiveData.observe(this@TransactionHistoryFragment, Observer {
                        isLastPage = it
                    })
                }
            }
        })
    }
}