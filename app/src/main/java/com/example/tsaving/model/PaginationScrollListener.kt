package com.example.tsaving.model

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(var layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    private var currentPage: Int = 1
    private var isCurrentlyLoading = false

    private fun isLoading(): Boolean {
        return isCurrentlyLoading
    }

    abstract fun loadItems(page: Int, recyclerView: RecyclerView)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        var lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (!isLoading() && dy > 0 && (lastVisibleItem + visibleItemCount) > totalItemCount / 2) {
            currentPage++
            loadItems(currentPage, recyclerView)
        }
    }
}