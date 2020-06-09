package com.huda.eftarramdanvideos.ElearningFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.huda.eftarramdanvideos.Models.AllVideoResponse
import com.huda.eftarramdanvideos.Models.QuestionData
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ElarningRepository {
    private val isLoading = MutableLiveData<Boolean>()

    fun getIsLoading(): MutableLiveData<Boolean> {
        Log.i("hhh", "" + isLoading.toString())
        return isLoading
    }


    fun getElearningQuestion(accessToken: String): MutableLiveData<QuestionData> {
        val userData = MutableLiveData<QuestionData>()
        isLoading.value = true
        Webservice.getInstance().api.getVideoQuestions(accessToken)
            .enqueue(object : Callback<QuestionData> {
                override fun onResponse(
                    call: Call<QuestionData>,
                    response: Response<QuestionData>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                        isLoading.value = false
                    } else {
                        userData.value = response.body()
                        isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<QuestionData>, t: Throwable) {
                    isLoading.value = false
                    userData.value = null
                }
            })

        return userData

    }

    fun getVideos(accessToken: String): MutableLiveData<AllVideoResponse> {
        val userData = MutableLiveData<AllVideoResponse>()
        isLoading.value = true
        Webservice.getInstance().api.getAllVideos(accessToken)
            .enqueue(object : Callback<AllVideoResponse> {
                override fun onResponse(
                    call: Call<AllVideoResponse>,
                    response: Response<AllVideoResponse>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                        isLoading.value = false
                    } else {
                        userData.value = response.body()
                        isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<AllVideoResponse>, t: Throwable) {
                    isLoading.value = false
                    userData.value = null
                }
            })

        return userData

    }
}
