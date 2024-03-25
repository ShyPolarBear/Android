package com.beeeam.feed.feedTotal

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.beeeam.base.BaseFragment
import com.beeeam.feed.R
import com.beeeam.feed.databinding.FragmentFeedTotalBinding
import com.beeeam.util.Const.POWER_MENU_OFFSET_X
import com.beeeam.util.Const.POWER_MENU_OFFSET_Y
import com.beeeam.util.FeedTotalLikeBtnType
import com.beeeam.util.infiniteScroll
import com.beeeam.util.showLikeBtnIsLike
import com.beeeam.feed.feedTotal.adapter.FeedPostAdapter
import com.beeeam.util.PowerMenuUtil
import com.beeeam.util.WriteChangeDivider
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedTotalFragment : BaseFragment<FragmentFeedTotalBinding, FeedTotalViewModel>(
    R.layout.fragment_feed_total,
) {

    override val viewModel: FeedTotalViewModel by viewModels()

    private var feedSort: String = "recent"
    private val feedPostAdapter = FeedPostAdapter(
        onMyPostPropertyClick = { view: ImageView, feedId: Int, position: Int ->
            showMyPostPropertyMenu(view, feedId, position)
        },
        onOtherPostPropertyClick = { view: ImageView ->
            showOtherPostPropertyMenu(view)
        },
        onMyBestCommentPropertyClick = { view: ImageView, commentId: Int, content: String, commentView: View ->
            showMyBestCommentPropertyMenu(view, commentId, content, commentView)
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
                commentId: Int?,
                itemType: FeedTotalLikeBtnType, ->
            changeLikeBtn(btn, isLiked, likeCnt, textView, feedId, commentId, itemType)
        },
        onMoveToDetailClick = { feedId: Int -> showFeedPostDetail(feedId) },
    )
    private val feedSortItems: List<PowerMenuItem> by lazy {
        listOf(
            PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_recent)),
            PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_recent_best)),
            PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_best)),
        )
    }

    override fun initView() {
        binding.apply {
            binding.progressFeedTotalLoading.isVisible = true
            binding.layoutFeed.isVisible = false

            loadSortedFeed(feedSort)
            setFeedPost()

            swipeLayoutFeedPost.setOnRefreshListener {
                loadSortedFeed(feedSort)
                swipeLayoutFeedPost.isRefreshing = false
            }

            ivFeedToolbarSort.setOnClickListener {
                PowerMenuUtil.getPowerMenu(
                    requireContext(),
                    viewLifecycleOwner,
                    feedSortItems,
                ) { _, item ->
                    when (item.title) {
                        getString(com.beeeam.designsystem.R.string.feed_post_property_recent) -> {
                            loadSortedFeed("recent")
                        }
                        getString(com.beeeam.designsystem.R.string.feed_post_property_recent_best) -> {
                            loadSortedFeed("recentBest")
                        }
                        getString(com.beeeam.designsystem.R.string.feed_post_property_best) -> {
                            loadSortedFeed("best")
                        }
                    }
                }.showAsDropDown(
                    ivFeedToolbarSort,
                    POWER_MENU_OFFSET_X,
                    POWER_MENU_OFFSET_Y,
                )
            }

            btnFeedPostWrite.setOnClickListener {
                findNavController().navigate(
                    FeedTotalFragmentDirections.actionNavigationFeedToFeedWriteFragment(
                        WriteChangeDivider.WRITE,
                        0,
                    ),
                )
            }

            rvFeedPost.infiniteScroll {
                when (viewModel.feedIsLast) {
                    true -> { }
                    false -> { viewModel.loadFeedTotalData(feedSort) }
                }
            }
        }
    }

    private fun loadSortedFeed(sort: String) {
        feedSort = sort
        viewModel.clearFeedList()
        viewModel.loadFeedTotalData(sort)
    }

    private fun setFeedPost() {
        binding.rvFeedPost.adapter = feedPostAdapter
        lifecycleScope.launch {
            viewModel.feed.observe(viewLifecycleOwner) {
                feedPostAdapter.submitList(it.toList())
                binding.progressFeedTotalLoading.isVisible = false
                binding.layoutFeed.isVisible = true
            }
        }
    }

    private fun showMyPostPropertyMenu(view: ImageView, feedId: Int, position: Int) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_delete)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myCommentPropertyItems,
        ) { _, item ->
            when (item.title) {
                getString(com.beeeam.designsystem.R.string.feed_post_property_revise) -> {
                    findNavController().navigate(
                        FeedTotalFragmentDirections.actionNavigationFeedToFeedWriteFragment(
                            WriteChangeDivider.CHANGE,
                            feedId,
                        ),
                    )
                }
                getString(com.beeeam.designsystem.R.string.feed_post_property_delete) -> {
                    viewModel.requestDeleteFeed(feedId)
                    viewModel.removeFeedList(position)
                }
            }
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }

    private fun showOtherPostPropertyMenu(view: ImageView) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_block)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myCommentPropertyItems,
        ) { _, _ ->
            Toast.makeText(requireContext(), getString(com.beeeam.designsystem.R.string.features_in_preparation), Toast.LENGTH_SHORT).show()
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }

    private fun showMyBestCommentPropertyMenu(view: ImageView, commentId: Int, content: String, commentView: View) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_delete)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myCommentPropertyItems,
        ) { _, item ->
            when (item.title) {
                getString(com.beeeam.designsystem.R.string.feed_post_property_revise) -> {
                    findNavController().navigate(FeedTotalFragmentDirections.actionNavigationFeedToFeedCommentChangeFragment(commentId, content))
                }
                getString(com.beeeam.designsystem.R.string.feed_post_property_delete) -> {
                    viewModel.requestDeleteFeedComment(commentId)
                    commentView.isVisible = false
                }
            }
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }

    private fun showOtherBestCommentPropertyMenu(view: ImageView) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_block)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myCommentPropertyItems,
        ) { _, _ ->
            Toast.makeText(requireContext(), getString(com.beeeam.designsystem.R.string.features_in_preparation), Toast.LENGTH_SHORT).show()
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }

    private fun changeLikeBtn(
        button: Button,
        isLiked: Boolean,
        likeCnt: Int,
        likeCntText: TextView,
        feedId: Int,
        commentId: Int?,
        itemType: FeedTotalLikeBtnType,
    ) {
        var isLike = isLiked
        var likeCount = likeCnt

        isLike = !isLike

        when (isLike) {
            true -> likeCount += 1
            false -> likeCount -= 1
        }

        when (itemType) {
            FeedTotalLikeBtnType.POST_LIKE_BTN ->
                viewModel.clickFeedLikeBtn(isLike, likeCount, feedId)

            FeedTotalLikeBtnType.BEST_COMMENT_LIKE_BTN ->
                viewModel.clickFeedBestCommentLikeBtn(isLike, likeCount, commentId!!)
        }

        button.showLikeBtnIsLike(isLike, button)
        likeCntText.text = likeCount.toString()
    }

    private fun showFeedPostDetail(feedId: Int) {
        findNavController().navigate(FeedTotalFragmentDirections.actionFeedTotalFragmentToFeedDetailFragment(feedId))
    }
}