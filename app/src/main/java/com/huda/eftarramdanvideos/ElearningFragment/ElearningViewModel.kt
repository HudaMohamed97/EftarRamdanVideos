package com.huda.eftarramdanvideos.ElearningFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.eftarramdanvideos.Models.QuestionData

class ElearningViewModel : ViewModel() {
    private var repositoryHelper: ElarningRepository = ElarningRepository()
    private lateinit var mutableLiveData: MutableLiveData<QuestionData>


    fun getElarningQuestion(accessToken: String) {
        mutableLiveData = repositoryHelper.getElearningQuestion(accessToken)

    }

    fun getData(): MutableLiveData<QuestionData> {
        return mutableLiveData
    }
}
