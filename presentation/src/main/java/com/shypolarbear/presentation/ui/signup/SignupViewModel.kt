package com.shypolarbear.presentation.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shypolarbear.presentation.base.BaseViewModel

class SignupViewModel:BaseViewModel(){
    private var _pageNumber = MutableLiveData<Int>()
    val pageNumber: LiveData<Int> = _pageNumber

}