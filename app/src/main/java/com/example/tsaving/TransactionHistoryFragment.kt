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
    private val firstPage = 1

    private lateinit var paginationScrollListener: PaginationScrollListener

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

        paginationScrollListener = object : PaginationScrollListener(
            rv_transaction_history.layoutManager as LinearLayoutManager
        ) {
            override var isCurrentlyLoading: Boolean = false

            override fun loadItems(page: Int, recyclerView: RecyclerView) {
                getDataFromViewModel(page, recyclerView)
            }
        }

        getDataFromViewModel(firstPage, rv_transaction_history)

        rv_transaction_history?.addOnScrollListener(paginationScrollListener)
    }

    private fun getDataFromViewModel(page: Int, view: RecyclerView) {
        transactionHistoryViewModel.apply {
            paginationScrollListener.isCurrentlyLoading = true
            isLastPageMutableLiveData.observe(this@TransactionHistoryFragment, Observer {
                isLastPage = it
            })

            if (!isLastPage) {
                transactionHistoryViewModel.listTransactionHistory(page)
                transactionHistoryMutableLiveData.observe(
                    this@TransactionHistoryFragment,
                    Observer {
                        transactionHistoryAdapter.transactionHistoryList = it
                        paginationScrollListener.isCurrentlyLoading = false
                        transactionHistoryAdapter.notifyDataSetChanged()
                    })
            }

            errorMutableLiveData.observe(this@TransactionHistoryFragment, Observer {
                ErrorDialogHandling(
                    view.context,
                    it.errorName,
                    it.errorMessage
                ).errorResponseDialog()
                paginationScrollListener.isCurrentlyLoading = false
            })
        }
    }
}