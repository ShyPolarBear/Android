package com.beeeam.feed.feedDetail

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import com.beeeam.base.BaseFragment
import com.beeeam.feed.R
import com.beeeam.feed.databinding.FragmentFeedDetailBinding
import com.beeeam.util.CommentLoadType
import com.beeeam.util.CommentType
import com.beeeam.util.Const.POWER_MENU_OFFSET_X
import com.beeeam.util.Const.POWER_MENU_OFFSET_Y
import com.beeeam.util.Const.fragmentTotalStatus
import com.beeeam.util.FeedDetailLikeBtnType
import com.beeeam.util.FragmentTotalStatus
import com.beeeam.util.PowerMenuUtil
import com.beeeam.util.WriteChangeDivider
import com.beeeam.util.infiniteScroll
import com.beeeam.util.showLikeBtnIsLike
import com.shypolarbear.domain.model.feed.Comment
import com.shypolarbear.domain.model.feed.Feed
import com.beeeam.feed.feedDetail.adapter.FeedCommentAdapter
import com.beeeam.feed.feedDetail.adapter.FeedDetailPostAdapter
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedDetailFragment : BaseFragment<FragmentFeedDetailBinding, FeedDetailViewModel>(
    R.layout.fragment_feed_detail,
) {

    override val viewModel: FeedDetailViewModel by viewModels()
    private var commentType = CommentType.COMMENT
    private var commentParentId = 0
    private var commentPosition = 0
    private val feedDetailArgs: FeedDetailFragmentArgs by navArgs()
    private val feedDetailPostAdapter: FeedDetailPostAdapter by lazy {
        FeedDetailPostAdapter(
            onPostPropertyClick = { feedDetail: Feed, imageView: ImageView ->
                showPostPropertyMenu(feedDetail, imageView)
            },
            onBtnLikeClick = {
                    btn: Button,
                    isLiked: Boolean,
                    likeCnt: Int,
                    textView: TextView,
                    commentId: Int,
                    replyId: Int,
                    itemType: FeedDetailLikeBtnType, ->
                changeLikeBtn(btn, isLiked, likeCnt, textView, commentId, replyId, itemType)
            },
            onBtnBackClick = { moveToBack() },
        )
    }
    private val feedCommentAdapter: FeedCommentAdapter by lazy {
        FeedCommentAdapter(
            onMyCommentPropertyClick = { view: ImageView, commentId: Int, position: Int, commentAuthor: String, content: String ->
                showMyCommentPropertyMenu(view, commentId, position, commentAuthor, content)
            },
            onOtherCommentPropertyClick = { view: ImageView, commentId: Int, position: Int, commentAuthor: String ->
                showOtherCommentPropertyMenu(view, commentId, position, commentAuthor)
            },
            onMyReplyPropertyClick = { view: ImageView, commentId: Int, _: Int, content: String ->
                showMyReplyPropertyMenu(view, commentId, feedDetailArgs.feedId, content)
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
                    itemType: FeedDetailLikeBtnType, ->
                changeLikeBtn(btn, isLiked, likeCnt, textView, commentId, replyId, itemType)
            },
            onItemClick = { setCommentWriteMode() }, // 선택된 댓글 해제
        )
    }

    override fun initView() {
        binding.apply {
            layoutFeedDetail.isVisible = false
            progressFeedDetailLoading.isVisible = true

            rvFeedDetail.adapter = ConcatAdapter(feedDetailPostAdapter, feedCommentAdapter)

            layoutFeedDetail.setOnClickListener {
                binding.edtFeedDetailReply.clearFocus()
            }

            btnFeedCommentWritingClose.setOnClickListener {
                binding.cardviewFeedCommentWritingMsg.isVisible = false
                setCommentWriteMode()
            }

            btnFeedCommentWrite.setOnClickListener {
                when (commentType) {
                    CommentType.COMMENT -> {
                        viewModel.requestWriteFeedComment(feedDetailArgs.feedId, edtFeedDetailReply.text.toString(), requireContext().getString(com.beeeam.designsystem.R.string.time_format))
                    }
                    CommentType.REPLY -> {
                        viewModel.requestWriteFeedReply(feedDetailArgs.feedId, commentParentId, edtFeedDetailReply.text.toString(), requireContext().getString(com.beeeam.designsystem.R.string.time_format), commentPosition)
                    }
                }
                binding.edtFeedDetailReply.clearFocus()
                binding.edtFeedDetailReply.setText("")
                binding.cardviewFeedCommentWritingMsg.isVisible = false
                setCommentWriteMode() // 대댓글 모드 해제
            }

            rvFeedDetail.infiniteScroll {
                val currentCommentList = viewModel.feedComment.value!!.toMutableList()

                when (viewModel.commentIsLast) {
                    true -> { }
                    false -> {
                        feedCommentAdapter.submitList(currentCommentList + listOf(Comment()))
                        viewModel.commentLoadType = CommentLoadType.COMMENT_LOAD
                        viewModel.loadFeedComment(feedDetailArgs.feedId, viewModel.commentLoadType)
                    }
                }
            }

            viewModel.getMyInfo()
            viewModel.loadFeedDetail(feedDetailArgs.feedId)
            viewModel.loadFeedComment(feedDetailArgs.feedId, viewModel.commentLoadType)

            viewModel.feed.observe(viewLifecycleOwner) { feed ->
                feedDetailPostAdapter.submitList(listOf(feed))
                binding.layoutFeedDetail.isVisible = true
                binding.progressFeedDetailLoading.isVisible = false
            }

            viewModel.feedComment.observe(viewLifecycleOwner) { comment ->
                viewModel.loadFeedDetail(feedDetailArgs.feedId)
                feedCommentAdapter.submitList(comment.toList())
            }
        }
    }

    override fun onBackPressed() {
        fragmentTotalStatus = FragmentTotalStatus.POST_CHANGE_OR_DETAIL_BACK
        findNavController().popBackStack()
    }

    private fun showMyCommentPropertyMenu(view: ImageView, commentId: Int, position: Int, commentAuthor: String, content: String) {
        val myCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_delete)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_comment_reply)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myCommentPropertyItems,
        ) { _, item ->
            when (item.title) {
                getString(com.beeeam.designsystem.R.string.feed_post_property_revise) -> {
                    viewModel.commentLoadType = CommentLoadType.INIT
                    findNavController().navigate(
                        FeedDetailFragmentDirections.actionFeedDetailFragmentToFeedCommentChangeFragment(
                            commentId,
                            content
                        )
                    )
                }
                getString(com.beeeam.designsystem.R.string.feed_post_property_delete) -> {
                    viewModel.requestDeleteFeedComment(commentId, position)
                }
                getString(com.beeeam.designsystem.R.string.feed_comment_reply) -> {
                    commentType = CommentType.REPLY
                    commentParentId = commentId
                    commentPosition = position
                    setReplyWriteMode(commentAuthor)
                }
            }
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }

    private fun showOtherCommentPropertyMenu(view: ImageView, commentId: Int, position: Int, commentAuthor: String) {
        val otherCommentPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_block)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_comment_reply)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            otherCommentPropertyItems,
        ) { _, item ->
            when (item.title) {
                getString(com.beeeam.designsystem.R.string.feed_post_property_report),
                getString(com.beeeam.designsystem.R.string.feed_post_property_block) -> {
                    Toast.makeText(requireContext(), getString(com.beeeam.designsystem.R.string.features_in_preparation), Toast.LENGTH_SHORT).show()
                }
                getString(com.beeeam.designsystem.R.string.feed_comment_reply) -> {
                    commentType = CommentType.REPLY
                    commentParentId = commentId
                    commentPosition = position
                    setReplyWriteMode(commentAuthor)
                }
            }
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }

    private fun showMyReplyPropertyMenu(view: ImageView, commentId: Int, feedId: Int, content: String) {
        val myReplyPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_delete)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myReplyPropertyItems,
        ) { _, item ->
            when (item.title) {
                getString(com.beeeam.designsystem.R.string.feed_post_property_revise) -> {
                    viewModel.commentLoadType = CommentLoadType.INIT
                    findNavController().navigate(
                        FeedDetailFragmentDirections.actionFeedDetailFragmentToFeedCommentChangeFragment(
                            commentId,
                            content
                        )
                    )
                }
                getString(com.beeeam.designsystem.R.string.feed_post_property_delete) -> {
                    viewModel.requestDeleteFeedReply(commentId, feedId)
                }
            }
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }

    private fun showOtherReplyPropertyMenu(view: ImageView) {
        val otherReplyPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_report)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_block)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            otherReplyPropertyItems,
        ) { _, item ->
            when (item.title) {
                getString(com.beeeam.designsystem.R.string.feed_post_property_report),
                getString(com.beeeam.designsystem.R.string.feed_post_property_block) -> {
                    Toast.makeText(requireContext(), getString(com.beeeam.designsystem.R.string.features_in_preparation), Toast.LENGTH_SHORT).show()
                }
            }
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
        commentId: Int = 0,
        replyId: Int = 0,
        itemType: FeedDetailLikeBtnType,
    ) {
        var isLike = isLiked
        var likeCount = likeCnt

        isLike = !isLike

        when (isLike) {
            true -> likeCount += 1
            false -> likeCount -= 1
        }

        when (itemType) {
            FeedDetailLikeBtnType.POST_LIKE_BTN ->
                viewModel.clickFeedPostLikeBtn(
                    isLiked = isLike,
                    likeCnt = likeCount,
                    feedId = feedDetailArgs.feedId,
                )

            FeedDetailLikeBtnType.COMMENT_LIKE_BTN ->
                viewModel.clickCommentLikeBtn(
                    isLiked = isLike,
                    likeCnt = likeCount,
                    commentId = commentId,
                )

            FeedDetailLikeBtnType.REPLY_LIKE_BTN ->
                viewModel.clickReplyLikeBtn(
                    isLiked = isLike,
                    likeCnt = likeCount,
                    parentCommentId = commentId,
                    replyId = replyId,
                )
        }

        button.showLikeBtnIsLike(isLike, button)
        likeCntText.text = likeCount.toString()
    }

    private fun showPostPropertyMenu(feedDetail: Feed, view: ImageView) {
        val postPropertyItems: List<PowerMenuItem>

        when (feedDetail.isAuthor) {
            true -> {
                postPropertyItems =
                    listOf(
                        PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_revise)),
                        PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_delete)),
                    )
            }
            false -> {
                postPropertyItems =
                    listOf(
                        PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_report)),
                        PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_block)),
                    )
            }
        }

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            postPropertyItems,
        ) { _, item ->
            when (item.title) {
                getString(com.beeeam.designsystem.R.string.feed_post_property_revise) -> {
                    findNavController().navigate(
                        FeedDetailFragmentDirections.actionFeedDetailFragmentToFeedWriteFragment(
//                            WriteChangeDivider.CHANGE,
                            feedDetailArgs.feedId,
                        ),
                    )
                }
                getString(com.beeeam.designsystem.R.string.feed_post_property_delete) -> {
                    viewModel.requestDeleteFeed(feedDetailArgs.feedId)
                    fragmentTotalStatus = FragmentTotalStatus.POST_CHANGE_OR_DETAIL_BACK
                    findNavController().popBackStack()
                }
                getString(com.beeeam.designsystem.R.string.feed_post_property_report),
                getString(com.beeeam.designsystem.R.string.feed_post_property_block) -> {
                    Toast.makeText(requireContext(), getString(com.beeeam.designsystem.R.string.features_in_preparation), Toast.LENGTH_SHORT).show()
                }
            }
        }.showAsDropDown(
            view,
            POWER_MENU_OFFSET_X,
            POWER_MENU_OFFSET_Y,
        )
    }

    private fun moveToBack() {
        fragmentTotalStatus = FragmentTotalStatus.POST_CHANGE_OR_DETAIL_BACK
        findNavController().popBackStack()
    }

    private fun setReplyWriteMode(commentAuthor: String) {
//        나중에 대댓글 옵션 클릭된 댓글 배경 바꿀 때 사용할 예정
//        view.selectedComment(true, view)

        commentType = CommentType.REPLY

        binding.edtFeedDetailReply.hint = getString(com.beeeam.designsystem.R.string.feed_detail_reply_msg)
        binding.cardviewFeedCommentWritingMsg.isVisible = true
        binding.tvFeedCommentWritingMsg.text = requireContext().getString(com.beeeam.designsystem.R.string.feed_detail_comment_writing_msg, commentAuthor)
    }

    private fun setCommentWriteMode() {
//        나중에 대댓글 옵션 클릭된 댓글 배경 바꿀 때 사용할 예정
//        view.selectedComment(false, view)

        commentType = CommentType.COMMENT
        commentParentId = 0
        commentPosition = 0

        binding.edtFeedDetailReply.hint = getString(com.beeeam.designsystem.R.string.feed_detail_comment_msg)
    }
}
