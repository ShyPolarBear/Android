package com.shypolarbear.presentation.ui.quiz.main

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentQuizMainBinding
import com.shypolarbear.presentation.ui.feed.feedTotal.WriteChangeDivider
import com.shypolarbear.presentation.ui.quiz.QuizViewModel
import com.shypolarbear.presentation.util.EventObserver
import com.shypolarbear.presentation.util.QuizNavType
import com.shypolarbear.presentation.util.setQuizNavigation
import com.shypolarbear.presentation.util.setSpecificTextColor
import dagger.hilt.android.AndroidEntryPoint

const val MAX_PAGES = 5

@AndroidEntryPoint
class QuizMainFragment :
    BaseFragment<FragmentQuizMainBinding, QuizViewModel>(R.layout.fragment_quiz_main) {
    override val viewModel: QuizViewModel by activityViewModels()

    override fun initView() {
        val recentFeedAdapter = QuizMainAdapter()
        binding.quizMainRv.adapter = recentFeedAdapter
        viewModel.requestDailyQuizSolvedState()
        viewModel.loadFeedRecentData()
        viewModel.getMyInfo()
        viewModel.feed.observe(viewLifecycleOwner) { recentFeed ->
            recentFeed?.let {
                recentFeedAdapter.submitList(recentFeed)
            }
        }
        viewModel.userName.observe(viewLifecycleOwner) { userName ->
            userName?.let {
                binding.quizMainTvName.setSpecificTextColor(
                    getString(R.string.quiz_main_user_name, userName),
                    userName,
                    styleId = R.style.H3,
                    colorId = R.color.Blue_01
                )
            }
        }
        viewModel.dailySubmit.observe(viewLifecycleOwner) { solvedState ->
            if (solvedState) {
                binding.quizMainTvGoQuiz.text = getString(R.string.quiz_main_tv_go_quiz)
            }
        }
        viewModel.quizResponse.observe(viewLifecycleOwner, EventObserver { quiz ->
            setQuizNavigation(quiz.type, QuizNavType.MAIN)
        })
        viewModel.reviewResponse.observe(viewLifecycleOwner, EventObserver { reviewQuiz ->
            setQuizNavigation(reviewQuiz.content.first().type, QuizNavType.MAIN)
        })

        binding.apply {
            quizMainTvTitle.setSpecificTextColor(
                getString(R.string.quiz_main_title),
                getString(R.string.quiz_main_polarbear),
                colorId = R.color.Blue_01
            )
            quizMainBtnGoQuiz.setOnClickListener {
                if (viewModel.dailySubmit.value!!) {
                    viewModel.requestReviewQuiz()
                } else viewModel.requestQuiz()
            }
            quizMainTvMore.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_quiz_main_to_navigation_feed)
            }
            quizMainBtnWrite.setOnClickListener {
                findNavController().navigate(QuizMainFragmentDirections.actionNavigationQuizMainToFeedWriteFragment(WriteChangeDivider.WRITE, 0))
            }
        }
    }
}