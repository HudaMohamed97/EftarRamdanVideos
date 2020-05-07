package com.huda.eftarramdanvideos.ElearningFragment

import androidx.lifecycle.MutableLiveData
import com.huda.eftarramdanvideos.Models.QuestionData
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ElarningRepository {
    fun getElearningQuestion(accessToken: String): MutableLiveData<QuestionData> {
        val userData = MutableLiveData<QuestionData>()
        Webservice.getInstance().api.getVideoQuestions(accessToken)
            .enqueue(object : Callback<QuestionData> {
                override fun onResponse(
                    call: Call<QuestionData>,
                    response: Response<QuestionData>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<QuestionData>, t: Throwable) {
                    userData.value = null
                }
            })

        return userData

    }
}
