package com.shypolarbear.presentation.ui.noti

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.noti.NotificationContent
import com.shypolarbear.domain.model.ranking.RankingScroll
import com.shypolarbear.domain.usecase.noti.LoadNotificationsUseCase
import com.shypolarbear.domain.usecase.noti.RequestNotificationReadUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.util.simpleHttpErrorCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotiViewModel @Inject constructor(
    private val notificationsUseCase: LoadNotificationsUseCase,
    private val notificationReadUseCase: RequestNotificationReadUseCase,
) : BaseViewModel() {
    private val _notificationList = MutableLiveData<List<NotificationContent>>()
    val notificationList: LiveData<List<NotificationContent>> = _notificationList
    private fun loadNotification() {
        viewModelScope.launch {
            notificationsUseCase()
                .onSuccess { response ->
                    _notificationList.value = response.data.notifications
                    Timber.tag("NOTI").d("${response.data}")
                }
                .onFailure { error ->
                    simpleHttpErrorCheck(error)
                }
        }
    }
}