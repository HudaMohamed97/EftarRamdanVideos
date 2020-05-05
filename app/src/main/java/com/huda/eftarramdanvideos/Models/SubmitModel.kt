package com.huda.eftarramdanvideos.Models

import com.google.gson.annotations.SerializedName

data class SubmitModel(
    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String
)