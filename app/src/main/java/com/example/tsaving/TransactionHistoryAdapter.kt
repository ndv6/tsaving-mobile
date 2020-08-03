package com.example.tsaving

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tsaving.helpers.TransactionHistoryHelper
import com.example.tsaving.model.response.TransactionHistoryResponseData

class TransactionHistoryAdapter :
    RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder>() {

    var transactionHistoryList: MutableList<TransactionHistoryResponseData> = mutableListOf()

    class TransactionHistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(position: TransactionHistoryResponseData) {
            val transactionHistoryIcon =
                view.findViewById<ImageView>(R.id.img_transaction_history)
            val tranAmt =
                view.findViewById<TextView>(R.id.tv_transaction_history_item_tran_amount)
            val destAccount =
                view.findViewById<TextView>(R.id.tv_transaction_history_item_dest_account)
            val fromAccount =
                view.findViewById<TextView>(R.id.tv_transaction_history_item_from_account)
            val desc = view.findViewById<TextView>(R.id.tv_transaction_history_item_title)
            val createdAt =
                view.findViewById<TextView>(R.id.tv_transaction_history_item_created_at)

            /* Placeholder string is used to prevent lint.
                    the IDE does not allow concatenating string on TextView's setText
             */
            val fromAccountPlaceholderString = "From : " + position.fromAccount
            val destAccountPlaceholderString = "To      : " + position.destinationAccount

            TransactionHistoryHelper.setImgSrc(position.description, transactionHistoryIcon)
            tranAmt.text = position.transferAmount.currencyFormatter("IDR")
            TransactionHistoryHelper.formatTransactionAmount(position.description, tranAmt)

            destAccount.text = destAccountPlaceholderString
            fromAccount.text = fromAccountPlaceholderString
            desc.text = position.description
            createdAt.text = position.createdAt.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionHistoryViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_history_item, parent, false)
        return TransactionHistoryViewHolder(viewItem)
    }

    override fun getItemCount(): Int = transactionHistoryList.size

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        holder.bindData(transactionHistoryList[position])
    }
}
