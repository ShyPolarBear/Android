package com.shypolarbear.domain.usecase.noti

import com.shypolarbear.domain.model.noti.NotificationResponse
import com.shypolarbear.domain.repository.noti.NotificationRepo

class LoadNotificationsUseCase(
    private val repo: NotificationRepo
) {
    suspend operator fun invoke(): Result<NotificationResponse>{
        return repo.loadNotificationList()
    }
}