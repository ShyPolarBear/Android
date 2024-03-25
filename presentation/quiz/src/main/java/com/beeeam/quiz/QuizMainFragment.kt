package com.beeeam.quiz

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.beeeam.base.BaseFragment
import com.beeeam.quiz.adapter.QuizMainAdapter
import com.beeeam.quiz.databinding.FragmentQuizMainBinding
import com.beeeam.util.EventObserver
import com.beeeam.util.QuizNavType
import com.beeeam.util.WriteChangeDivider
import com.beeeam.util.createNavDeepLinkRequest
import com.beeeam.util.setSpecificTextColor
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class QuizMainFragment :
    BaseFragment<FragmentQuizMainBinding, QuizMainViewModel>(R.layout.fragment_quiz_main) {
    override val viewModel: QuizMainViewModel by activityViewModels()

    override fun initView() {
        val recentFeedAdapter = QuizMainAdapter(onMoveToDetailClick = { feedId: Int -> showFeedPostDetail(feedId) })
        binding.quizMainRv.adapter = recentFeedAdapter
        viewModel.requestDailyQuizSolvedState()
        viewModel.loadFeedRecentData()
        viewModel.getMyInfo()
        viewModel.feed.observe(viewLifecycleOwner) { recentFeed ->
            Timber.d(recentFeed.toString())
            recentFeed?.let {
                recentFeedAdapter.submitList(recentFeed)
            }
        }
        viewModel.userName.observe(viewLifecycleOwner) { userName ->
            userName?.let {
                binding.quizMainTvName.setSpecificTextColor(
                    getString(com.beeeam.designsystem.R.string.quiz_main_user_name, userName),
                    userName,
                    styleId = com.beeeam.designsystem.R.style.H3,
                    colorId = com.beeeam.designsystem.R.color.Blue_01,
                )
            }
        }
        viewModel.dailySubmit.observe(viewLifecycleOwner) { solvedState ->
            if (solvedState) {
                binding.quizMainTvGoQuiz.text = getString(com.beeeam.designsystem.R.string.quiz_main_tv_go_quiz)
            }
        }
        viewModel.quizResponse.observe(
            viewLifecycleOwner,
            EventObserver { quiz ->
                setQuizNavigation(quiz.type, QuizNavType.MAIN)
            },
        )
        viewModel.reviewResponse.observe(
            viewLifecycleOwner,
            EventObserver { reviewQuiz ->
                setQuizNavigation(reviewQuiz.content.first().type, QuizNavType.MAIN)
            },
        )

        binding.apply {
            quizMainTvTitle.setSpecificTextColor(
                getString(com.beeeam.designsystem.R.string.quiz_main_title),
                getString(com.beeeam.designsystem.R.string.quiz_main_polarbear),
                colorId = com.beeeam.designsystem.R.color.Blue_01,
            )
            quizMainBtnGoQuiz.setOnClickListener {
                if (viewModel.dailySubmit.value!!) {
                    viewModel.requestReviewQuiz()
                } else {
                    viewModel.requestQuiz()
                }
            }
            quizMainTvMore.setOnClickListener {
                findNavController().navigate(createNavDeepLinkRequest("shyPolarBear://fragmentFeedTotal"))
            }
            quizMainBtnWrite.setOnClickListener {
                findNavController().navigate(
                    createNavDeepLinkRequest("shyPolarBear://fragmentFeedWrite/${WriteChangeDivider.WRITE}/${0}")
                )
            }
        }
    }

    private fun showFeedPostDetail(feedId: Int) {
        findNavController().navigate(createNavDeepLinkRequest("shyPolarBear://fragmentFeedDetail/${feedId}"))
    }
}
