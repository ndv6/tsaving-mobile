package com.example.tsaving

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tsaving.model.TransactionHistoryResponseBody

class TransactionHistoryAdapter :
    RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder>() {

    var transactionHistoryList: List<TransactionHistoryResponseBody> = listOf()

    class TransactionHistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(position: TransactionHistoryResponseBody) {
            val tranAmt =
                view.findViewById<TextView>(R.id.tv_transaction_history_item_tran_amount)
            val destAccount =
                view.findViewById<TextView>(R.id.tv_transaction_history_item_dest_account)
            val fromAccount =
                view.findViewById<TextView>(R.id.tv_transaction_history_item_from_account)
            val desc = view.findViewById<TextView>(R.id.tv_transaction_history_item_title)
            val createdAt =
                view.findViewById<TextView>(R.id.tv_transaction_history_item_created_at)

            tranAmt.text = position.transferAmount.toString()
            destAccount.text = position.destinationAccount
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
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_history_item, null)
        return TransactionHistoryViewHolder(viewItem)
    }

    override fun getItemCount(): Int = transactionHistoryList.size

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        holder.bindData(transactionHistoryList[position])
    }
}
