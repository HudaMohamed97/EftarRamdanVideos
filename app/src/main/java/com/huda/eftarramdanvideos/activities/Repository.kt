package com.imagin.myapplication.LoginFragment

import com.huda.eftarramdanvideos.Models.QuestionData
import com.huda.eftarramdanvideos.Models.SubmitModel
import com.huda.eftarramdanvideos.Models.VideoResponse
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {



    fun submitStartVideo(
        videoId: Int,
        accessToken: String
    ) {
        Webservice.getInstance().api.startVideos(videoId, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                    } else {
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                }
            })
        return
    }


    fun submitEndVideo(
        videoId: Int,
        accessToken: String
    ) {
        Webservice.getInstance().api.endVideos(videoId, accessToken)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>,
                    response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                    } else {
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                }
            })
    }


}



