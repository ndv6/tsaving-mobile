package com.example.tsaving

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tsaving.model.VirtualAccount

class DashboardRecyclerViewAdapter(val listener : (VirtualAccount) -> Unit): RecyclerView.Adapter<DashboardRecyclerViewAdapter.DashboardItemHolder>() {
    var vaList: List<VirtualAccount> = listOf<VirtualAccount>()

     inner class DashboardItemHolder (val view: View) :RecyclerView.ViewHolder(view) {
        fun bindData(va: VirtualAccount) {
            val tvLabel = view.findViewById<TextView>(R.id.tv_item_list_va_label)
            val tvBalance = view.findViewById<TextView>(R.id.tv_item_list_va_balance)
            val tvVaNum = view.findViewById<TextView>(R.id.tv_item_list_va_num)
            val cvDetail = view.findViewById<CardView>(R.id.cv_item_list_va)
            var tvColor = view.findViewById<TextView>(R.id.tv_item_list_va_color)

            tvLabel.text = va.vaLabel
            tvVaNum.text = va.vaNum
            tvBalance.text = "Rp. ${va.vaBalance}"
            if (va.vaColor == "Red") {
                tvColor.setBackgroundResource(R.color.colorRed)
            } else if (va.vaColor == "Orange") {
                tvColor.setBackgroundResource(R.color.colorOrange)
            } else if (va.vaColor == "Green") {
                tvColor.setBackgroundResource(R.color.colorGreen)
            }else if (va.vaColor == "Yellow"){
                tvColor.setBackgroundResource(R.color.colorYellow)
            }else if(va.vaColor == "Blue"){
                tvColor.setBackgroundResource(R.color.colorBlue)
            }else if(va.vaColor == "Purple"){
                tvColor.setBackgroundResource(R.color.colorPurple)
            }
            cvDetail.setOnClickListener {
                listener(va)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardItemHolder {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.va_item_list, parent,false)
        return DashboardItemHolder((layoutView))
    }

    override fun getItemCount(): Int = vaList.size

    override fun onBindViewHolder(holder: DashboardItemHolder, position: Int) {
        holder.bindData(vaList.elementAt(position))
    }
}