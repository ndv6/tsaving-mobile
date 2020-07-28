package com.example.tsaving.model

import java.util.*

class TransactionLog (id: Int, accNum: String, from: String, dest: String, amount: Int, desc: String, created: Date) {
    val transactionLogId = id
    val accountNum = accNum
    val fromAccount = from
    val destinationAccount = dest
    val transferAmount = amount
    val description = desc
    val createdAt = created
}