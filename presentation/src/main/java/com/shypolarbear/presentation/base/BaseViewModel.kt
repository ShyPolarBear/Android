package com.shypolarbear.presentation.base

import androidx.lifecycle.ViewModel

open class BaseViewModel() : ViewModel(){
    // 로그용 태그
    private val TAG = this.javaClass.simpleName
}