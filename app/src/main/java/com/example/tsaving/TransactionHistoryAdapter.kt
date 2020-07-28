package com.example.tsaving

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.sql.Time

class TransactionHistoryAdapter :
    RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder>() {

    data class TransactionHistory(
        var accountNum: String,
        var tranAmount: Int,
        var destAccount: String,
        var fromAccount: String,
        var description: String,
        var createdAt: Time
    )

    var transactionHistoryList: MutableList<TransactionHistory> = mutableListOf()

    class TransactionHistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(position: TransactionHistory) {
            val accountNum = view.findViewById<TextView>(R.id.text_transaction_history_account_num)
            val tranAmt =
                view.findViewById<TextView>(R.id.text_transaction_history_item_tran_amount)
            val destAccount =
                view.findViewById<TextView>(R.id.text_transaction_history_item_dest_account)
            val fromAccount =
                view.findViewById<TextView>(R.id.text_transaction_history_item_from_account)
            val desc = view.findViewById<TextView>(R.id.text_transaction_history_item_title)
            val createdAt =
                view.findViewById<TextView>(R.id.text_transaction_history_item_created_at)

            accountNum.text = position.accountNum
            tranAmt.text = position.tranAmount.toString()
            destAccount.text = position.destAccount
            fromAccount.text = position.fromAccount
            desc.text = position.description
            createdAt.text = position.createdAt.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionHistoryViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_history, null)
        return TransactionHistoryViewHolder(viewItem)
    }

    override fun getItemCount(): Int = transactionHistoryList.size

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        holder.bindData(transactionHistoryList[position])
    }
}
