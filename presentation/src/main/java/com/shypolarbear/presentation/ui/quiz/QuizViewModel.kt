package com.shypolarbear.presentation.ui.quiz

import com.shypolarbear.domain.usecase.quiz.QuizUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizUseCase: QuizUseCase
): BaseViewModel() {
}