package com.example.tsaving.model

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    private var currentPage: Int = 1

    abstract var isCurrentlyLoading: Boolean
    abstract fun loadItems(page: Int, recyclerView: RecyclerView)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastCompletelyVisibleItemPosition()


        if (!isCurrentlyLoading && dy > 0 && lastVisibleItem == totalItemCount - 1) {
            currentPage++
            loadItems(currentPage, recyclerView)
        }
    }
}