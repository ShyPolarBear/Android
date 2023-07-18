package com.shypolarbear.domain.repository.feed

import com.shypolarbear.domain.model.feed.FeedTotal

interface FeedTotalRepo {
    suspend fun getFeedTotalData(): Result<FeedTotal>
}