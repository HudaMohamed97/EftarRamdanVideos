package com.huda.eftarramdanvideos.EventDateFragment

import androidx.lifecycle.MutableLiveData
import com.huda.eftarramdanvideos.Models.AgendaModel
import com.huda.eftarramdanvideos.Models.QuestionData
import com.huda.eftarramdanvideos.NetworkLayer.Webservice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AgendaRepository {
    fun getAgenda(): MutableLiveData<AgendaModel> {
        val userData = MutableLiveData<AgendaModel>()
        Webservice.getInstance().api.getSettings()
            .enqueue(object : Callback<AgendaModel> {
                override fun onResponse(
                    call: Call<AgendaModel>,
                    response: Response<AgendaModel>
                ) {
                    if (response.isSuccessful) {
                        userData.value = response.body()
                    } else {
                        userData.value = response.body()
                    }
                }

                override fun onFailure(call: Call<AgendaModel>, t: Throwable) {
                    userData.value = null
                }
            })

        return userData

    }

}
