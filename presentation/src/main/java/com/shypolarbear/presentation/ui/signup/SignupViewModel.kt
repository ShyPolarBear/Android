package com.shypolarbear.presentation.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shypolarbear.presentation.base.BaseViewModel

class SignupViewModel:BaseViewModel(){
    private val termData = MutableLiveData<String>()
    private val nameData = MutableLiveData<String>()
    private val phoneData = MutableLiveData<String>()
    private val mailData = MutableLiveData<String>()

    fun getTermData(): LiveData<String> {
        return termData
    }

    fun setTermData(newData: String) {
        termData.value = newData
    }
    fun getNameData(): LiveData<String> {
        return nameData
    }

    fun setNameData(newData: String) {
        nameData.value = newData
    }
    fun getPhoneData(): LiveData<String> {
        return phoneData
    }

    fun setPhoneData(newData: String) {
        phoneData.value = newData
    }
    fun getMailData(): LiveData<String> {
        return mailData
    }

    fun setMailData(newData: String) {
        mailData.value = newData
    }
}