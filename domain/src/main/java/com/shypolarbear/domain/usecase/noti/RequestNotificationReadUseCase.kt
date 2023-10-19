package com.shypolarbear.domain.usecase.noti

import com.shypolarbear.domain.model.noti.NotificationReadResponse
import com.shypolarbear.domain.repository.noti.NotificationRepo

class RequestNotificationReadUseCase(
    private val repo: NotificationRepo,
) {
    suspend operator fun invoke(notificationId: Int): Result<NotificationReadResponse> {
        return repo.requestNotificationRead(notificationId)
    }
}