package com.shypolarbear.domain.model.quiz

data class SubmitRequestOX(
    val isTimeout: Boolean,
    val answer: String?,
)

data class SubmitRequestMulti(
    val isTimeout: Boolean,
    val answerId: Long?,
)
