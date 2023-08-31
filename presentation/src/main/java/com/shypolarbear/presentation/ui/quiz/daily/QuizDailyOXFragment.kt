package com.shypolarbear.presentation.ui.quiz.daily

import androidx.fragment.app.activityViewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyOxBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.QuizType
import com.shypolarbear.presentation.util.detectActivation
import com.shypolarbear.presentation.util.setReviewMode
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class QuizDailyOXFragment :
    BaseFragment<FragmentQuizDailyOxBinding, QuizViewModel>(R.layout.fragment_quiz_daily_ox) {
    override val viewModel: QuizViewModel by activityViewModels()
    private lateinit var dialog: QuizDialog
    override fun initView() {
        dialog = QuizDialog(requireContext())
        val state: DialogType = DialogType.INCORRECT // viewModel로 갈 예정

        viewModel.submitBtnState.observe(viewLifecycleOwner) { submitState ->
            submitState?.let {
                binding.quizDailyTvSubmit.isActivated = submitState
                binding.quizDailyBtnSubmit.isActivated = submitState
            }
        }

        viewModel.submitResponse.observe(viewLifecycleOwner){ reponse ->
            reponse?.let {
                dialog.showDialog(
                    viewModel.submitResponse.value!!.isCorrect,
                    viewModel.submitResponse.value!!.explanation,
                    viewModel.submitResponse.value!!.point.toString()
                )
            }
        }

        binding.apply {
            val choiceList = listOf(quizDailyO, quizDailyX)

            choiceList.map { choice ->
                choice.setOnClickListener {
                    val answer = choice.text.toString()
                    viewModel.setAnswer(answer)

                    choice.detectActivation(*choiceList.filter { other ->
                        other != choice
                    }.toTypedArray())
                }
            }

            viewModel.quizResponse.observe(viewLifecycleOwner) { quiz ->
                quiz?.let {
                    quizDailyProblem.text = quiz.question
                }
            }

            quizDailyBtnBack.setReviewMode(
                state,
                quizDailyPages,
                dialog,
                R.id.action_quizDailyOXFragment_to_quizMainFragment
            )

            quizDailyBtnSubmit.setOnClickListener {
                quizDailyBtnSubmit.setOnClickListener {
                    viewModel.submitAnswer(QuizType.OX)
                }
            }
        }
    }
}