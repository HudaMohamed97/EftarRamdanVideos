package com.imagin.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import com.huda.eftarramdanvideos.Models.*
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import com.imagin.myapplication.Models.RegisterRequestModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterRepository {
    fun register(registerRequestModel: RegisterRequestModel): MutableLiveData<ResponseModelData> {
        val userData = MutableLiveData<ResponseModelData>()
        Webservice.getInstance().api.register(registerRequestModel)
            .enqueue(object : Callback<ResponseModelData> {
                override fun onResponse(
                    call: Call<ResponseModelData>,
                    response: Response<ResponseModelData>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                    } else {
                        if (response.code() == 422) {
                            val dummyResponse =
                                ResponseModelData(
                                    "",
                                    "this email is already token please enter valid Email",
                                    0,
                                    Account()
                                )
                            userData.value = dummyResponse
                        } else {
                            userData.value = response.body()
                        }

                    }
                }

                override fun onFailure(call: Call<ResponseModelData>, t: Throwable) {
                    userData.value = null
                }
            })

        return userData

    }

    fun getScore(access: String): MutableLiveData<MyScore> {
        val userData = MutableLiveData<MyScore>()
        Webservice.getInstance().api.getScore(access)
            .enqueue(object : Callback<MyScore> {
                override fun onResponse(
                    call: Call<MyScore>,
                    response: Response<MyScore>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<MyScore>, t: Throwable) {
                    userData.value = null
                }
            })

        return userData

    }

    fun resetEmail(
        email: String
    ): MutableLiveData<SubmitModel> {

        val answerData = MutableLiveData<SubmitModel>()
        val body = mapOf(
            "email" to email
        )
        Webservice.getInstance().api.reset(body)
            .enqueue(object : Callback<SubmitModel> {
                override fun onResponse(
                    call: Call<SubmitModel>, response: Response<SubmitModel>
                ) {
                    if (response.isSuccessful) {
                        answerData.value = response.body()
                    } else {
                        if (response.code() == 400) {
                            answerData.value = SubmitModel("NoAccount", "NoAccount")
                        } else if (response.code() == 422) {
                            answerData.value = SubmitModel("NotValid", "NotValid")

                        } else {
                            answerData.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    answerData.value = null
                }
            })
        return answerData
    }
}



