package com.example.tsaving

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tsaving.model.VirtualAccount

class DashboardRecyclerViewAdapter: RecyclerView.Adapter<DashboardRecyclerViewAdapter.DashboardItemHolder>() {
    val vaList = mutableListOf<VirtualAccount>()

    data class DashboardItemHolder (val view: View) :RecyclerView.ViewHolder(view) {
        fun bindData(va: VirtualAccount) {
            val tvLabel = view.findViewById<TextView>(R.id.tv_item_list_va_label)
            val tvBalance = view.findViewById<TextView>(R.id.tv_item_list_va_balance)
            val btnVaDetail = view.findViewById<Button>(R.id.btn_item_list_va_detail)
            tvLabel.text = va.vaLabel
            tvBalance.text = va.vaBalance.toString()

            btnVaDetail.setOnClickListener {
                view.context.startActivity(Intent(view.context, VADetailsActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardItemHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.va_item_list, null)
        return DashboardItemHolder((layoutView))
    }

    override fun getItemCount(): Int = vaList.size

    override fun onBindViewHolder(holder: DashboardItemHolder, position: Int) {
        holder.bindData(vaList.elementAt(position))
    }
}