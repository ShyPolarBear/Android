package com.shypolarbear.presentation.ui.quiz.daily

import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.domain.model.quiz.Choice
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizDailyMultiBinding
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.ui.quiz.daily.dialog.BackDialog
import com.shypolarbear.presentation.ui.quiz.daily.dialog.QuizDialog
import com.shypolarbear.presentation.util.DialogType
import com.shypolarbear.presentation.util.QuizType
import com.shypolarbear.presentation.util.detectActivation
import timber.log.Timber

class QuizDailyMultiChoiceFragment :
    BaseFragment<FragmentQuizDailyMultiBinding, QuizViewModel>(
        R.layout.fragment_quiz_daily_multi
    ) {
    private lateinit var dialog: QuizDialog
    override val viewModel: QuizViewModel by activityViewModels()
    /* TODO:
    *  손 봐야야 할 것
    *  복습문제 조회, 채점,
    *  리뷰모드 시 뒤로가기
    *  복습문제 다음문제로 넘어가기
    *  데일리 퀴즈 풀이여부 판단
    * */
    override fun initView() {
        dialog = QuizDialog(requireContext())
        val backBtn = BackDialog(requireContext())
        val state: DialogType = DialogType.REVIEW // viewModel로 갈 예정

//        viewModel.dailySubmit.observe(viewLifecycleOwner) { dailyState ->
//            dailyState?.let {
//                dialog.alertDialog.setOnDismissListener {
//                    findNavController().navigate(R.id.action_quizDailyMultiChoiceFragment_to_quizMainFragment)
//                }
//            }
//        }
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
            val choiceList =
                listOf(quizDailyChoice1, quizDailyChoice2, quizDailyChoice3, quizDailyChoice4)

            choiceList.map { choice ->
                choice.setOnClickListener {
                    val id = quizInstance.choices!![choiceList.indexOf(choice)].id
                    viewModel.setAnswer(id.toString())

                    choice.detectActivation(*choiceList.filter { other ->
                        other != choice
                    }.toTypedArray())
                }
            }

            quizDailyProblem.text = quizInstance.question
            initChoices(choiceList, quizInstance.choices!!)

            quizDailyBtnBack.setOnClickListener {
                findNavController().popBackStack()
                Timber.tag("BACK").d("BACK")
            }

//            quizDailyBtnBack.setReviewMode(
//                state,
//                quizDailyPages,
//                backBtn,
//                R.id.action_quizDailyMultiChoiceFragment_to_quizMainFragment
//            )

            quizDailyBtnSubmit.setOnClickListener {
                viewModel.submitAnswer(QuizType.MULTI)
            }
        }
    }

    private fun initChoices(choiceList: List<TextView>, textList: List<Choice>? = null) {
        for ((answerText, choice) in choiceList.withIndex()) {
            textList?.let {
                choice.text = textList[answerText].text
            }
        }
    }
}