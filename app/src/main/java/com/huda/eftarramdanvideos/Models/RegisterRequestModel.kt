package com.imagin.myapplication.Models

import com.google.gson.annotations.SerializedName

data class RegisterRequestModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String,
    @SerializedName("company") val company : String,
    @SerializedName("title") val title : String,
    @SerializedName("phone") val phone: String,
    @SerializedName("country") val country: String
)