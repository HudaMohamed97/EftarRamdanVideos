package com.imagin.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import com.huda.eftarramdanvideos.Models.Account
import com.huda.eftarramdanvideos.Models.ResponseModelData
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
                                    "",
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


}



