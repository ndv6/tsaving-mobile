package com.example.tsaving.model

import java.util.*

class Account (accId: Int, accNum: String, accBalance: Int, created: Date) {
    var accountId: Int = accId
    var accountNum: String = accNum
    var accountBalance: Int = accBalance
    var createdAt: Date = created
}