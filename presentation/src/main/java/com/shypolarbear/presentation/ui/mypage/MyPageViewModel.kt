package com.shypolarbear.presentation.ui.mypage

import com.shypolarbear.domain.usecase.mypage.GetMyPostUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyPostUseCase: GetMyPostUseCase,
) : BaseViewModel() {

}