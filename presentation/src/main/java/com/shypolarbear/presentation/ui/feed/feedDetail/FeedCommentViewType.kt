package com.shypolarbear.presentation.ui.feed.feedDetail

enum class FeedCommentViewType(val commentType: Int) {
    COMMENT_NORMAL(0),
    COMMENT_DELETE(1),
    REPLY_NORMAL(2),
    REPLY_DELETE(3)
}