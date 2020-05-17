package com.huda.eftarramdanvideos.EventDateFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.eftarramdanvideos.Models.AgendaModel

class EventViewModel:ViewModel() {

    private var repositoryHelper: AgendaRepository = AgendaRepository()
    private lateinit var mutableLiveData: MutableLiveData<AgendaModel>


    fun getAgenda() {
        mutableLiveData = repositoryHelper.getAgenda()

    }

    fun getData(): MutableLiveData<AgendaModel> {
        return mutableLiveData
    }

}
