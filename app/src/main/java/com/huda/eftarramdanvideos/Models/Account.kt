package com.huda.eftarramdanvideos.Models

import com.google.gson.annotations.SerializedName


data class Account(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String? = "name",
    @SerializedName("email") val email: String? = "email",
    @SerializedName("company") val company: String? = "company",
    @SerializedName("title") val title: String? = "title",
    @SerializedName("phone") val phone: String? = "phone",
    @SerializedName("keep_updated") val keep_updated: Boolean? = true,
    @SerializedName("score") val score: Int = 0
)
