package com.imagin.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import com.huda.eftarramdanvideos.Models.Account
import com.imagin.myapplication.Models.LoginRequestModel
import com.huda.eftarramdanvideos.Models.ResponseModelData
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginRepository {
    companion object {
        const val ERROR_CODE = 401   //this for Wrong Password
    }

    fun login(email: String, password: String): MutableLiveData<ResponseModelData> {
        val userData = MutableLiveData<ResponseModelData>()
        val body = LoginRequestModel(email.trim(), password)
        Webservice.getInstance().api.login(body).enqueue(object : Callback<ResponseModelData> {
            override fun onResponse(
                call: Call<ResponseModelData>,
                response: Response<ResponseModelData>
            ) {
                if (response.isSuccessful) {
                    userData.value = response.body()
                } else {
                    when {
                        response.code() == ERROR_CODE -> {
                            var jObjError: JSONObject? = null
                            try {
                                jObjError = JSONObject(response.errorBody()?.string())
                            } catch (e: Exception) {
                                e.message
                            }
                            val dummyResponse =
                                ResponseModelData(
                                    "",
                                    jObjError!!["title"].toString(),
                                    0,
                                    Account()
                                )
                            userData.value = dummyResponse
                        }
                        response.code() == 422 -> {
                            val dummyResponse =
                                ResponseModelData(
                                    "",
                                    "Password must be at leaast 6 length",
                                    0,
                                    Account()
                                )
                            userData.value = dummyResponse
                        }
                        else -> {
                            val dummyResponse =
                                ResponseModelData("", response.message(), 0, Account())
                            userData.value = dummyResponse
                        }
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



