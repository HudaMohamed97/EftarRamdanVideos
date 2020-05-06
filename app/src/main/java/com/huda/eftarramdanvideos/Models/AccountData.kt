package com.huda.eftarramdanvideos.Models

import com.google.gson.annotations.SerializedName


data class AccountData(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("score") val score: Int,
    @SerializedName("company") val company: String,
    @SerializedName("title") val title: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("keep_updated") val keep_updated: Boolean
)
