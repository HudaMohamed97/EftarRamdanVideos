package com.imagin.myapplication.LoginFragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huda.eftarramdanvideos.Models.Account
import com.huda.eftarramdanvideos.Models.AccountData
import com.huda.eftarramdanvideos.Models.MyScore
import com.huda.eftarramdanvideos.utilies.Validation
import com.imagin.myapplication.Models.RegisterRequestModel
import com.huda.eftarramdanvideos.Models.ResponseModelData


class RegisterViewModel : ViewModel() {
    private var repositoryHelper: RegisterRepository = RegisterRepository()
    private lateinit var mutableLiveData: MutableLiveData<ResponseModelData>
    private lateinit var liveData: MutableLiveData<MyScore>

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
}







