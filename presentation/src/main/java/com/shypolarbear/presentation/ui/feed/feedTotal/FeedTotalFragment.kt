package com.shypolarbear.presentation.ui.feed.feedTotal

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedTotalBinding
import com.shypolarbear.presentation.ui.feed.feedTotal.adapter.FeedPostAdapter
import com.shypolarbear.presentation.util.showLikeBtnIsLike
import com.shypolarbear.presentation.util.setMenu
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FeedTotalFragment: BaseFragment<FragmentFeedTotalBinding, FeedTotalViewModel> (
    R.layout.fragment_feed_total
){

    companion object {
        const val POWER_MENU_OFFSET_X = -290
        const val POWER_MENU_OFFSET_Y = 0
    }

    override val viewModel: FeedTotalViewModel by viewModels()
    private val feedSortItems: List<PowerMenuItem> by lazy {
        listOf(
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent_best)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_best))
        )
    }

    override fun initView() {

        binding.apply {
            viewModel.loadFeedTotalData()

            ivFeedToolbarSort.setOnClickListener {
                ivFeedToolbarSort.setMenu(
                    ivFeedToolbarSort,
                    feedSortItems,
                    viewLifecycleOwner
                )
            }
            setFeedPost()
        }
    }

    private fun setFeedPost() {
        val feedPostAdapter = FeedPostAdapter(
            onMyPostPropertyClick = { view: ImageView ->
                showMyPostPropertyMenu(view)
            },
            onOtherPostPropertyClick = { view: ImageView ->
                showOtherPostPropertyMenu(view)
            },
            onMyBestCommentPropertyClick = { view: ImageView ->
                showMyBestCommentPropertyMenu(view)
            },
            onOtherBestCommentPropertyClick = { view: ImageView ->
                showOtherBestCommentPropertyMenu(view)
            },
            onBtnLikeClick = {
                    btn: Button,
                    isLiked: Boolean,
                    likeCnt: Int,
                    textView: TextView,
                    feedId: Int,
                    itemType: FeedTotalLikeBtnType ->
                changeLikeBtn(btn, isLiked, likeCnt, textView, feedId, itemType)
            },
            onMoveToDetailClick = { feedId: Int -> showFeedPostDetail(feedId) }
        )

        binding.rvFeedPost.adapter = feedPostAdapter
        lifecycleScope.launch {
            viewModel.feed.observe(viewLifecycleOwner) {
                Timber.d("피드 리스트: $it")
                feedPostAdapter.submitList(it)
            }
        }
    }

    private fun showMyPostPropertyMenu(view: ImageView) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete))
            )

        view.setMenu(
            view,
            myCommentPropertyItems,
            viewLifecycleOwner
        )
    }

    private fun showOtherPostPropertyMenu(view: ImageView) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_block))
            )

        view.setMenu(
            view,
            myCommentPropertyItems,
            viewLifecycleOwner
        )
    }

    private fun showMyBestCommentPropertyMenu(view: ImageView) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete))
            )

        view.setMenu(
            view,
            myCommentPropertyItems,
            viewLifecycleOwner
        )
    }

    private fun showOtherBestCommentPropertyMenu(view: ImageView) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_block))
            )

        view.setMenu(
            view,
            myCommentPropertyItems,
            viewLifecycleOwner
        )
    }

    private fun changeLikeBtn(
        button: Button,
        isLiked: Boolean,
        likeCnt: Int,
        likeCntText: TextView,
        feedId: Int,
        itemType: FeedTotalLikeBtnType
        ) {
        var isLike = isLiked
        var likeCount = likeCnt

        isLike = !isLike

        when(isLike) {
            true -> likeCount += 1
            false -> likeCount -= 1
        }

        when (itemType) {
            FeedTotalLikeBtnType.POST_LIKE_BTN ->
                viewModel.clickFeedLikeBtn(isLike, likeCount, feedId)

            FeedTotalLikeBtnType.BEST_COMMENT_LIKE_BTN ->
                viewModel.clickFeedBestCommentLikeBtn(isLike, likeCount, feedId)
        }

        button.showLikeBtnIsLike(isLike, button)
        likeCntText.text = likeCount.toString()
    }

    private fun showFeedPostDetail(feedId: Int) {
        findNavController().navigate(
            FeedTotalFragmentDirections.actionFeedTotalFragmentToFeedDetailFragment(feedId)
        )
    }
}