package com.example.tsaving.vm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsaving.webservice.TsavingRepository
import kotlinx.coroutines.launch

class DashboardViewModel (private val tsRepo: TsavingRepository): ViewModel() {
    fun fetchDashboardData() {
        viewModelScope.launch {
            val req = tsRepo.dashboard()

        }
    }
}