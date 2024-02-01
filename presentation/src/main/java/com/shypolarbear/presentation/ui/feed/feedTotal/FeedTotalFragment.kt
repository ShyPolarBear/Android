package com.shypolarbear.presentation.ui.feed.feedTotal

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedTotalBinding
import com.shypolarbear.presentation.ui.feed.feedTotal.adapter.FeedPostAdapter
import com.shypolarbear.presentation.util.PowerMenuUtil
import com.shypolarbear.presentation.util.infiniteScroll
import com.shypolarbear.presentation.util.showLikeBtnIsLike
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

enum class WriteChangeDivider(val fragmentType: Int) {
    WRITE(0),
    CHANGE(1),
}

enum class FragmentTotalStatus(val status: Int) {
    INIT(0),
    WRITE_BACK_BTN_CLICK(1),
    POST_CHANGE_OR_DETAIL_BACK(2),
}

var fragmentTotalStatus = FragmentTotalStatus.INIT

@AndroidEntryPoint
class FeedTotalFragment : BaseFragment<FragmentFeedTotalBinding, FeedTotalViewModel> (
    R.layout.fragment_feed_total,
) {

    companion object {
        const val POWER_MENU_OFFSET_X = -290
        const val POWER_MENU_OFFSET_Y = 0
    }

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
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent_best)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_best)),
        )
    }

    override fun initView() {
        binding.apply {
            binding.progressFeedTotalLoading.isVisible = true
            binding.layoutFeed.isVisible = false

            when (fragmentTotalStatus) {
                FragmentTotalStatus.INIT, FragmentTotalStatus.POST_CHANGE_OR_DETAIL_BACK -> {
                    viewModel.clearFeedList()
                    viewModel.loadFeedTotalData(feedSort)
                }
                FragmentTotalStatus.WRITE_BACK_BTN_CLICK -> { }
            }
            setFeedPost()

            swipeLayoutFeedPost.setOnRefreshListener {
                viewModel.clearFeedList()
                viewModel.loadFeedTotalData(feedSort)
                swipeLayoutFeedPost.isRefreshing = false
            }

            ivFeedToolbarSort.setOnClickListener {
                PowerMenuUtil.getPowerMenu(
                    requireContext(),
                    viewLifecycleOwner,
                    feedSortItems,
                ) { _, item ->
                    when (item.title) {
                        getString(R.string.feed_post_property_recent) -> {
                            loadSortedFeed("recent")
                        }
                        getString(R.string.feed_post_property_recent_best) -> {
                            loadSortedFeed("recentBest")
                        }
                        getString(R.string.feed_post_property_best) -> {
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
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myCommentPropertyItems,
        ) { _, item ->
            when (item.title) {
                getString(R.string.feed_post_property_revise) -> {
                    findNavController().navigate(
                        FeedTotalFragmentDirections.actionNavigationFeedToFeedWriteFragment(
                            WriteChangeDivider.CHANGE,
                            feedId,
                        ),
                    )
                }
                getString(R.string.feed_post_property_delete) -> {
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
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_block)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myCommentPropertyItems,
        ) { _, _ ->
            Toast.makeText(requireContext(), getString(R.string.features_in_preparation), Toast.LENGTH_SHORT).show()
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }

    private fun showMyBestCommentPropertyMenu(view: ImageView, commentId: Int, content: String, commentView: View) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myCommentPropertyItems,
        ) { _, item ->
            when (item.title) {
                getString(R.string.feed_post_property_revise) -> {
                    findNavController().navigate(FeedTotalFragmentDirections.actionNavigationFeedToFeedCommentChangeFragment(commentId, content))
                }
                getString(R.string.feed_post_property_delete) -> {
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
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_block)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myCommentPropertyItems,
        ) { _, _ ->
            Toast.makeText(requireContext(), getString(R.string.features_in_preparation), Toast.LENGTH_SHORT).show()
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

    private fun loadSortedFeed(sort: String) {
        feedSort = sort
        viewModel.clearFeedList()
        viewModel.loadFeedTotalData(sort)
    }
}
