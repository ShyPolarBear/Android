package com.shypolarbear.domain.model.noti

data class NotificationReadResponse(
    val code: Int,
    val data: NotificationId,
    val message: String,
)

data class NotificationId(
    val notificationId: Int
)
