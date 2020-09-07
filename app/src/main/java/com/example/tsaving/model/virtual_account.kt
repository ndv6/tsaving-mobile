package com.example.tsaving.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class VirtualAccount (
    @SerializedName("va_id")
    val vaId: Int?,
    @SerializedName("va_num")
    val vaNum: String?,
    @SerializedName("account_num")
    val accNum: String?,
    @SerializedName("va_balance")
    val vaBalance: Int,
    @SerializedName("va_color")
    val vaColor: String,
    @SerializedName("va_label")
    val vaLabel: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("updated_at")
    val updatedAt: Date) : Parcelable