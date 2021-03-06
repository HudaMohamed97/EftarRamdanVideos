package com.imagin.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.eftarramdanvideos.Models.*
import com.huda.eftarramdanvideos.utilies.Validation
import com.imagin.myapplication.Models.RegisterRequestModel


class RegisterViewModel : ViewModel() {
    private var repositoryHelper: RegisterRepository = RegisterRepository()
    private lateinit var mutableLiveData: MutableLiveData<ResponseModelData>
    private lateinit var liveData: MutableLiveData<MyScore>
    private lateinit var liveDataReset: MutableLiveData<SubmitModel>

    fun validateDataInfo(
        emailEt: String,
        passwordEt: String
    ): Boolean {
        val isEmailValid = Validation.validateEmail(emailEt)
        val isPasswordValid = Validation.validate(passwordEt)
        return !(!isEmailValid || !isPasswordValid)
    }

    fun register(registerRequestModel: RegisterRequestModel) {
        mutableLiveData = repositoryHelper.register(registerRequestModel)

    }

    fun getData(): MutableLiveData<ResponseModelData> {
        return mutableLiveData
    }

    fun getScore(access: String) {
        liveData = repositoryHelper.getScore(access)

    }

    fun getScoreData(): MutableLiveData<MyScore> {
        return liveData
    }

    fun resetPass(email: String) {
        liveDataReset = repositoryHelper.resetEmail(email)

    }

    fun getResetPassData(): MutableLiveData<SubmitModel> {
        return liveDataReset
    }
}







