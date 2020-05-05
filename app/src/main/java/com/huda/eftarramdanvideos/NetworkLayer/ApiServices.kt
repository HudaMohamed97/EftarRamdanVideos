package com.huda.eftarramdanvideos.NetworkLayer

import com.huda.eftarramdanvideos.Models.ResponseModelData
import com.huda.eftarramdanvideos.Models.SubmitModel
import com.huda.eftarramdanvideos.Models.VideoResponse
import com.imagin.myapplication.Models.LoginRequestModel
import com.imagin.myapplication.Models.RegisterRequestModel
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @POST("auth/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Call<ResponseModelData>

    @POST("auth/register")
    fun register(@Body registerRequestModel: RegisterRequestModel): Call<ResponseModelData>

    @GET("videos")
    fun getVideos(@Header("Authorization") authHeader: String): Call<VideoResponse>

    @POST("videos/{video}/start")
    fun startVideos(@Path("video") video: Int, @Header("Authorization") authHeader: String): Call<SubmitModel>

    @POST("videos/{video}/end")
    fun endVideos(@Path("video") video: Int, @Header("Authorization") authHeader: String): Call<SubmitModel>


}