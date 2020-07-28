package com.example.tsaving.model

import com.example.tsaving.model.Account
import com.example.tsaving.model.VirtualAccount

class DashboardModel (name: String, email: String, vas: List<VirtualAccount>, acc: Account) {
    var profileName : String = name
    var profileEmail: String = email
    var vaList: List<VirtualAccount> = vas
    var account: Account = acc
}
