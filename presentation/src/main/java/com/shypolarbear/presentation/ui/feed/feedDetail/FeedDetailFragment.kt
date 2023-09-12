package com.shypolarbear.presentation.ui.feed.feedDetail

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedDetailBinding
import com.shypolarbear.presentation.ui.common.ImageViewPagerAdapter
import com.shypolarbear.presentation.ui.feed.feedDetail.adapter.FeedCommentAdapter
import com.shypolarbear.presentation.ui.feed.feedTotal.FragmentTotalStatus
import com.shypolarbear.presentation.ui.feed.feedTotal.fragmentTotalStats
import com.shypolarbear.presentation.util.GlideUtil
import com.shypolarbear.presentation.util.showLikeBtnIsLike
import com.shypolarbear.presentation.util.setMenu
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedDetailFragment : BaseFragment<FragmentFeedDetailBinding, FeedDetailViewModel>(
    R.layout.fragment_feed_detail
) {

    override val viewModel: FeedDetailViewModel by viewModels()
    private val feedDetailArgs: FeedDetailFragmentArgs by navArgs()
    private val feedCommentAdapter: FeedCommentAdapter by lazy {
        FeedCommentAdapter(
            onMyCommentPropertyClick = { view: ImageView ->
                showMyCommentPropertyMenu(view)
            },
            onOtherCommentPropertyClick = { view: ImageView ->
                showOtherCommentPropertyMenu(view)
            },
            onMyReplyPropertyClick = { view: ImageView ->
                showMyReplyPropertyMenu(view)
            },
            onOtherReplyPropertyClick = { view: ImageView ->
                showOtherReplyPropertyMenu(view)
            },
            onBtnLikeClick = {
                    btn: Button,
                    isLiked: Boolean,
                    likeCnt: Int,
                    textView: TextView,
                    commentId: Int,
                    replyId: Int,
                    itemType: FeedDetailLikeBtnType ->
                changeLikeBtn(btn, isLiked, likeCnt, textView, commentId, replyId, itemType)
            }
        )
    }

    override fun initView() {

        binding.apply {
            layoutFeedDetail.isVisible = false
            progressFeedDetailLoading.isVisible = true

            btnFeedDetailBack.setOnClickListener {
                fragmentTotalStats = FragmentTotalStatus.POST_CHANGE
                findNavController().popBackStack()
            }

            edtFeedDetailReply.setOnFocusChangeListener { _, isFocus ->
                binding.cardviewFeedCommentWritingMsg.isVisible = isFocus
            }

            layoutFeedDetail.setOnClickListener {
                binding.edtFeedDetailReply.clearFocus()
            }

            btnFeedCommentWritingClose.setOnClickListener {
                binding.cardviewFeedCommentWritingMsg.isVisible = false
            }

            viewModel.loadFeedDetail(feedDetailArgs.feedId)
            viewModel.loadFeedComment(feedDetailArgs.feedId)

            viewModel.feed.observe(viewLifecycleOwner) {feed ->
                setFeedPost(feed)
            }
            setFeedComment()
        }
    }

    private fun setFeedPost(feedDetail: Feed) {
        var postPropertyItems: List<PowerMenuItem>
        val isPostLike = feedDetail.isLike
        val postLikeCnt: Int = feedDetail.likeCount

        // 댓글 기능 미구현 상황이라 임시로 여기에 정의
        // TODO("댓글 기능 구현되면 제거")
        binding.layoutFeedDetail.isVisible = true
        binding.progressFeedDetailLoading.isVisible = false

        binding.tvFeedDetailUserNickname.text = feedDetail.author
        binding.tvFeedDetailPostingTime.text = feedDetail.createdDate
        binding.tvFeedDetailLikeCnt.text = feedDetail.likeCount.toString()
        binding.tvFeedDetailTitle.text = feedDetail.title
        binding.tvFeedDetailContent.text = feedDetail.content
        binding.tvFeedDetailReplyCnt.text = feedDetail.commentCount.toString()

        binding.btnFeedDetailLike.showLikeBtnIsLike(feedDetail.isLike, binding.btnFeedDetailLike)
        binding.btnFeedDetailLike.setOnClickListener {
            changeLikeBtn(
                button = binding.btnFeedDetailLike,
                isLiked = isPostLike,
                likeCnt = postLikeCnt,
                likeCntText = binding.tvFeedDetailLikeCnt,
                itemType = FeedDetailLikeBtnType.POST_LIKE_BTN
            )
        }

        if (!feedDetail.authorProfileImage.isNullOrBlank()) {
            GlideUtil.loadImage(requireContext(), feedDetail.authorProfileImage, binding.ivFeedDetailUserProfile)
        } else {
            GlideUtil.loadImage(requireContext(), url = null, view = binding.ivFeedDetailUserProfile, placeHolder = R.drawable.ic_user_base_profile)
        }

        if (feedDetail.commentCount == 0)
            binding.rvFeedDetailReply.isVisible = false

        with(binding.viewpagerFeedDetailImg) {
            adapter = ImageViewPagerAdapter().apply {
                submitList(feedDetail.feedImages)
            }

            TabLayoutMediator(binding.tablayoutFeedDetailIndicator, this
            ) { _, _ ->

            }.attach()
        }


        binding.ivFeedDetailProperty.setOnClickListener {
            when (feedDetail.isAuthor) {
                true -> {
                    postPropertyItems =
                        listOf(
                            PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
                            PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete))
                        )
                }
                false -> {
                    postPropertyItems =
                        listOf(
                            PowerMenuItem(requireContext().getString(R.string.feed_post_property_report)),
                            PowerMenuItem(requireContext().getString(R.string.feed_post_property_block))
                        )
                }
            }

            binding.ivFeedDetailProperty.setMenu(
                binding.ivFeedDetailProperty,
                postPropertyItems,
                viewLifecycleOwner
            )
        }
    }

    private fun setFeedComment() {
        binding.rvFeedDetailReply.adapter = feedCommentAdapter
        viewModel.feedComment.observe(viewLifecycleOwner) {
            feedCommentAdapter.submitList(it)
            binding.layoutFeedDetail.isVisible = true
            binding.progressFeedDetailLoading.isVisible = false
        }
    }

    private fun showMyCommentPropertyMenu(view: ImageView) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete)),
                PowerMenuItem(requireContext().getString(R.string.feed_comment_reply))
            )

        view.setMenu(
            view,
            myCommentPropertyItems,
            viewLifecycleOwner
        )
    }

    private fun showOtherCommentPropertyMenu(view: ImageView) {
        val otherCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_block)),
                PowerMenuItem(requireContext().getString(R.string.feed_comment_reply))
            )

        view.setMenu(
            view,
            otherCommentPropertyItems,
            viewLifecycleOwner
        )
    }

    private fun showMyReplyPropertyMenu(view: ImageView) {
        val myReplyPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete)),
                PowerMenuItem(requireContext().getString(R.string.feed_comment_reply))
            )

        view.setMenu(
            view,
            myReplyPropertyItems,
            viewLifecycleOwner
        )
    }

    private fun showOtherReplyPropertyMenu(view: ImageView) {
        val otherReplyPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_block)),
                PowerMenuItem(requireContext().getString(R.string.feed_comment_reply))
            )

        view.setMenu(
            view,
            otherReplyPropertyItems,
            viewLifecycleOwner
        )
    }

    private fun changeLikeBtn(
        button: Button,
        isLiked: Boolean,
        likeCnt: Int,
        likeCntText: TextView,
        commentId: Int = 0,
        replyId: Int = 0,
        itemType: FeedDetailLikeBtnType
    ) {
        var isLike = isLiked
        var likeCount = likeCnt

        isLike = !isLike

        when(isLike) {
            true -> likeCount += 1
            false -> likeCount -= 1
        }

        when(itemType) {
            FeedDetailLikeBtnType.POST_LIKE_BTN ->
                viewModel.clickFeedPostLikeBtn(
                    isLiked = isLike,
                    likeCnt = likeCount,
                    feedId = feedDetailArgs.feedId
                )

            FeedDetailLikeBtnType.COMMENT_LIKE_BTN ->
                viewModel.clickCommentLikeBtn(
                    isLiked = isLike,
                    likeCnt = likeCount,
                    commentId = commentId
                )

            FeedDetailLikeBtnType.REPLY_LIKE_BTN ->
                viewModel.clickReplyLikeBtn(
                    isLiked = isLike,
                    likeCnt = likeCount,
                    parentCommentId = commentId,
                    replyId = replyId
                )
        }

        button.showLikeBtnIsLike(isLike, button)
        likeCntText.text = likeCount.toString()
    }
}