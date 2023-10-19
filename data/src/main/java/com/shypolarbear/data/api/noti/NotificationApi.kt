package com.shypolarbear.data.api.noti

import com.shypolarbear.domain.model.noti.NotificationReadResponse
import com.shypolarbear.domain.model.noti.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationApi {
    @GET("/api/notifications")
    suspend fun loadNotificationsList(): Response<NotificationResponse>

    @PUT("/api/notifications/{notificationId}/read")
    suspend fun requestNotificationRead(
        @Path("notificationId") notificationId: Int
    ): Response<NotificationReadResponse>
}