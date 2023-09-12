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
import com.shypolarbear.presentation.databinding.FragmentFeedDetailNoImageBinding
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
class FeedDetailNoImageFragment : BaseFragment<FragmentFeedDetailNoImageBinding, FeedDetailViewModel>(
    R.layout.fragment_feed_detail_no_image
) {

    override val viewModel: FeedDetailViewModel by viewModels()
    private val feedDetailNoImageArgs: FeedDetailNoImageFragmentArgs by navArgs()
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

            btnFeedDetailNoImageBack.setOnClickListener {
                fragmentTotalStats = FragmentTotalStatus.BACK_BTN_CLICK
                findNavController().popBackStack()
            }

            edtFeedDetailNoImageReply.setOnFocusChangeListener { _, isFocus ->
                binding.cardviewFeedNoImageCommentWritingMsg.isVisible = isFocus
            }

            layoutFeedDetail.setOnClickListener {
                binding.edtFeedDetailNoImageReply.clearFocus()
            }

            btnFeedNoImageCommentWritingClose.setOnClickListener {
                binding.cardviewFeedNoImageCommentWritingMsg.isVisible = false
            }

            viewModel.loadFeedDetail(feedDetailNoImageArgs.feedId)
            viewModel.loadFeedComment(feedDetailNoImageArgs.feedId)

            viewModel.feed.observe(viewLifecycleOwner) { feed ->
                setFeedPost(feed)
            }
            setFeedComment()
        }
    }

    private fun setFeedPost(feedDetail: Feed) {
        var postPropertyItems: List<PowerMenuItem>
        val isPostLike = feedDetail.isLike
        val postLikeCnt: Int = feedDetail.likeCount

        // 댓글 기능 미구현 상황이라 임시로 여기에 정의 (상세 정보 받아오는 동안 progress bar 보여주는 동작)
        // TODO("댓글 기능 구현되면 제거")
        binding.layoutFeedDetail.isVisible = true
        binding.progressFeedDetailLoading.isVisible = false

        binding.tvFeedDetailNoImageUserNickname.text = feedDetail.author
        binding.tvFeedDetailNoImagePostingTime.text = feedDetail.createdDate
        binding.tvFeedDetailNoImageLikeCnt.text = feedDetail.likeCount.toString()
        binding.tvFeedDetailNoImageTitle.text = feedDetail.title
        binding.tvFeedDetailNoImageContent.text = feedDetail.content
        binding.tvFeedDetailNoImageReplyCnt.text = feedDetail.commentCount.toString()

        binding.btnFeedDetailNoImageLike.showLikeBtnIsLike(feedDetail.isLike, binding.btnFeedDetailNoImageLike)
        binding.btnFeedDetailNoImageLike.setOnClickListener {
            changeLikeBtn(
                button = binding.btnFeedDetailNoImageLike,
                isLiked = isPostLike,
                likeCnt = postLikeCnt,
                likeCntText = binding.tvFeedDetailNoImageLikeCnt,
                itemType = FeedDetailLikeBtnType.POST_LIKE_BTN
            )
        }

        if (!feedDetail.authorProfileImage.isNullOrBlank()) {
            GlideUtil.loadImage(requireContext(), feedDetail.authorProfileImage, binding.ivFeedDetailNoImageUserProfile)
        } else {
            GlideUtil.loadImage(requireContext(), url = null, view = binding.ivFeedDetailNoImageUserProfile, placeHolder = R.drawable.ic_user_base_profile)
        }

        if (feedDetail.commentCount == 0)
            binding.rvFeedDetailNoImageReply.isVisible = false

        binding.ivFeedDetailNoImageProperty.setOnClickListener {
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

            binding.ivFeedDetailNoImageProperty.setMenu(
                binding.ivFeedDetailNoImageProperty,
                postPropertyItems,
                viewLifecycleOwner
            )
        }
    }

    private fun setFeedComment() {
        binding.rvFeedDetailNoImageReply.adapter = feedCommentAdapter
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
                    likeCnt = likeCount
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