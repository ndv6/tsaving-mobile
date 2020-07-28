package com.example.tsaving

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.transaction_history.*

class TransactionHistoryFragment : androidx.fragment.app.Fragment() {
    val transactionHistoryAdapter = TransactionHistoryAdapter()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.transaction_history)
//        rv_transaction_history.adapter = transactionHistoryAdapter
//        rv_transaction_history.layoutManager = LinearLayoutManager(this)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.transaction_history, container, false)
    }
}