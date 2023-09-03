package com.shypolarbear.presentation.ui.quiz.daily

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyOxBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.daily.dialog.BackDialog
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.QuizType
import com.shypolarbear.presentation.util.detectActivation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizDailyOXFragment :
    BaseFragment<FragmentQuizDailyOxBinding, QuizViewModel>(R.layout.fragment_quiz_daily_ox) {
    override val viewModel: QuizViewModel by activityViewModels()
    private lateinit var dialog: QuizDialog
    override fun initView() {
        dialog = QuizDialog(requireContext())
        val backBtn = BackDialog(requireContext())

        val state: DialogType = DialogType.INCORRECT // viewModel로 갈 예정
        val quizInstance = viewModel.quizResponse.value!!.peekContent()

        viewModel.submitBtnState.observe(viewLifecycleOwner) { submitState ->
            submitState?.let {
                binding.quizDailyTvSubmit.isActivated = submitState
                binding.quizDailyBtnSubmit.isActivated = submitState
            }
        }

        viewModel.submitResponse.observe(viewLifecycleOwner) { response ->
            response?.let {
                dialog.showDialog(
                    response.isCorrect,
                    response.explanation,
                    response.point.toString(),
                    quizInstance.type
                )
            }
        }

        binding.apply {
            quizDailyProblem.text = quizInstance.question
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


//            quizDailyBtnBack.setReviewMode(
//                state,
//                quizDailyPages,
//                backBtn,
//                R.id.action_quizDailyOXFragment_to_quizMainFragment
//            )
            quizDailyBtnSubmit.setOnClickListener {
                viewModel.submitAnswer(QuizType.OX)
            }
        }
    }
}