package com.huda.eftarramdanvideos.ElearningFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.eftarramdanvideos.Models.AllVideoResponse
import com.huda.eftarramdanvideos.Models.QuestionData

class ElearningViewModel : ViewModel() {
    private var repositoryHelper: ElarningRepository = ElarningRepository()
    private lateinit var mutableLiveData: MutableLiveData<QuestionData>
    private lateinit var videoMutableLiveData: MutableLiveData<AllVideoResponse>
    private var isLoading: MutableLiveData<Boolean> = MutableLiveData()


    fun getElarningQuestion(accessToken: String) {
        mutableLiveData = repositoryHelper.getElearningQuestion(accessToken)

    }

    fun getData(): MutableLiveData<QuestionData> {
        return mutableLiveData

    }

    fun getAllVideos(accessToken: String) {
        videoMutableLiveData = repositoryHelper.getVideos(accessToken)

    }

    fun getVideosData(): MutableLiveData<AllVideoResponse> {
        return videoMutableLiveData

    }

    fun getIsLoading(): MutableLiveData<Boolean> {
        isLoading = repositoryHelper.getIsLoading()
        Log.i("hhh", "view model" + isLoading.value)
        return isLoading
    }
}
