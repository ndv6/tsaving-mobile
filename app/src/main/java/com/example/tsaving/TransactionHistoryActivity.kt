package com.example.tsaving

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.transaction_history.*

class TransactionHistoryActivity : Activity() {
    val transactionHistoryAdapter = TransactionHistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transaction_history)
        rv_transaction_history.adapter = transactionHistoryAdapter
        rv_transaction_history.layoutManager = LinearLayoutManager(this)
    }
}