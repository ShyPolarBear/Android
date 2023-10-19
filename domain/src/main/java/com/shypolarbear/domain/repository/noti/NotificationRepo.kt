package com.shypolarbear.domain.repository.noti

import com.shypolarbear.domain.model.noti.NotificationReadResponse
import com.shypolarbear.domain.model.noti.NotificationResponse

interface NotificationRepo {
    suspend fun loadNotificationList(): Result<NotificationResponse>
    suspend fun requestNotificationRead(notificationId: Int): Result<NotificationReadResponse>
}