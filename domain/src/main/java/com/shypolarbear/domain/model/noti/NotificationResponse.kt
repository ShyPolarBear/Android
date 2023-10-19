package com.shypolarbear.domain.model.noti

data class NotificationResponse(
    val code: Int,
    val data: NotificationData,
    val message: String
)

data class NotificationData(
    val notifications: List<NotificationContent>,
    val last: Boolean = true,
)

data class NotificationContent(
    val notificationId: Long,
    val notificationType: String,
    val redirectTarget: String = "Feed",
    val redirectTargetId: Int,
    val title: String,
    val content: String,
    val createdDate: String,
    val isRead: Boolean,
)