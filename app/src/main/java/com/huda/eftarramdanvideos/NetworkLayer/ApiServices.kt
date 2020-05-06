package com.huda.eftarramdanvideos.NetworkLayer

import SingelQuestionData
import com.huda.eftarramdanvideos.Models.*
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

    /*@GET("questions")
    fun getVideoQuestions(@Header("Authorization") authHeader: String): Call<QuestionData>
*/
    @GET("e-learning")
    fun getVideoQuestions(@Header("Authorization") authHeader: String): Call<QuestionData>

    @GET("account/me")
    fun getScore(@Header("Authorization") authHeader: String): Call<MyScore>

    @POST("videos/{video}/start")
    fun startVideos(@Path("video") video: Int, @Header("Authorization") authHeader: String): Call<SubmitModel>

    @GET("questions/{question}")
    fun getSingelQuestion(
        @Path("question") question: Int,
        @Header("Authorization") authHeader: String
    ): Call<SingelQuestionData>

    @POST("questions/{question}/send-answer")
    fun submitQuestion(
        @Path("question") question: Int,
        @Body body: Map<String, String>,
        @Header("Authorization") authHeader: String
    ): Call<SubmitModel>

    @POST("videos/{video}/end")
    fun endVideos(@Path("video") video: Int, @Header("Authorization") authHeader: String): Call<SubmitModel>


}