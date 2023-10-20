package com.shypolarbear.presentation.ui.noti

import com.shypolarbear.domain.usecase.noti.LoadNotificationsUseCase
import com.shypolarbear.domain.usecase.noti.RequestNotificationReadUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotiViewModel @Inject constructor(
    private val notificationsUseCase: LoadNotificationsUseCase,
    private val notificationReadUseCase: RequestNotificationReadUseCase,
) : BaseViewModel() {

}