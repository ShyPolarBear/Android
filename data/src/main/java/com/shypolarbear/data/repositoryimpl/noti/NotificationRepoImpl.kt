package com.shypolarbear.data.repositoryimpl.noti

import com.shypolarbear.data.api.noti.NotificationApi
import com.shypolarbear.domain.model.HttpError
import com.shypolarbear.domain.model.noti.NotificationReadResponse
import com.shypolarbear.domain.model.noti.NotificationResponse
import com.shypolarbear.domain.repository.noti.NotificationRepo
import javax.inject.Inject

class NotificationRepoImpl @Inject constructor(private val api: NotificationApi) :
    NotificationRepo {
    override suspend fun loadNotificationList(): Result<NotificationResponse> {
        return try {
            val response = api.loadNotificationsList()
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }

                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun requestNotificationRead(notificationId: Int): Result<NotificationReadResponse> {
        return try {
            val response = api.requestNotificationRead(notificationId)
            when {
                response.isSuccessful -> {
                    Result.success(response.body()!!)
                }

                else -> {
                    Result.failure(HttpError(response.code(), response.errorBody()?.string() ?: ""))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}