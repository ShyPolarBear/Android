package com.shypolarbear.domain.usecase.feed

import com.shypolarbear.domain.model.feed.feedDetail.FeedDetail
import com.shypolarbear.domain.repository.feed.FeedRepo

class FeedDetailUseCase (
    private val repo: FeedRepo
) {
    suspend operator fun invoke(feedId: Int): Result<FeedDetail> {
        return repo.getFeedDetailData(feedId)
    }
}