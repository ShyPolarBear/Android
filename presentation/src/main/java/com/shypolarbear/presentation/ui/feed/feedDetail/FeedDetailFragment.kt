package com.shypolarbear.presentation.ui.feed.feedDetail

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.shypolarbear.domain.model.feed.Feed
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedDetailBinding
import com.shypolarbear.presentation.ui.common.ImageViewPagerAdapter
import com.shypolarbear.presentation.ui.feed.feedDetail.adapter.FeedCommentAdapter
import com.shypolarbear.presentation.ui.feed.feedTotal.FeedTotalFragment.Companion.FEED_ID
import com.shypolarbear.presentation.util.showLike
import com.shypolarbear.presentation.util.setMenu
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedDetailFragment : BaseFragment<FragmentFeedDetailBinding, FeedDetailViewModel>(
    R.layout.fragment_feed_detail
) {

    override val viewModel: FeedDetailViewModel by viewModels()
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
            onBtnLikeClick = { btn: Button, isLiked: Boolean, likeCnt: Int, textView: TextView ->
                changeLikeBtn(btn, isLiked, likeCnt, textView)
            }
        )
    }

    override fun initView() {

        binding.btnFeedDetailBack.setOnClickListener {
            findNavController().navigate(R.id.action_feedDetailFragment_to_feedTotalFragment)
        }

        binding.edtFeedDetailReply.setOnFocusChangeListener { _, isFocus ->
            binding.cardviewFeedCommentWritingMsg.isVisible = isFocus
        }

        binding.layoutFeedDetail.setOnClickListener {
            binding.edtFeedDetailReply.clearFocus()
        }

        binding.btnFeedCommentWritingClose.setOnClickListener {
            binding.cardviewFeedCommentWritingMsg.isVisible = false
        }

        viewModel.loadFeedDetail(requireArguments().getString(FEED_ID)!!.toInt())
        viewModel.loadFeedCommentMock(requireArguments().getString(FEED_ID)!!.toInt())
        viewModel.loadFeedComment()

        viewModel.feed.observe(viewLifecycleOwner) {feed ->
            setFeedPost(feed)
        }
        setFeedComment()
    }

    private fun setFeedPost(feedDetail: Feed) {
        var isPostLike = feedDetail.isLike
        var postLikeCnt: Int = feedDetail.likeCount

        binding.tvFeedDetailUserNickname.text = feedDetail.author
        binding.tvFeedDetailPostingTime.text = feedDetail.createdDate
        binding.tvFeedDetailLikeCnt.text = feedDetail.likeCount.toString()
        binding.tvFeedDetailTitle.text = feedDetail.title
        binding.tvFeedDetailContent.text = feedDetail.content
        binding.tvFeedDetailReplyCnt.text = feedDetail.commentCount.toString()

        binding.btnFeedDetailLike.showLike(feedDetail.isLike, binding.btnFeedDetailLike)
        binding.btnFeedDetailLike.setOnClickListener {
            postLikeCnt = changeLikeBtn(
                binding.btnFeedDetailLike,
                isPostLike,
                postLikeCnt,
                binding.tvFeedDetailLikeCnt
            )
            isPostLike = !isPostLike
        }

        with(binding.viewpagerFeedDetailImg) {
            adapter = ImageViewPagerAdapter().apply {
                submitList(
                    // 테스트 데이터
                    feedDetail.feedImage
                )
            }

            TabLayoutMediator(binding.tablayoutFeedDetailIndicator, this
            ) { tab, position ->

            }.attach()
        }


        binding.ivFeedDetailProperty.setOnClickListener {
            when (feedDetail.isAuthor) {
                true -> {
                    val postPropertyItems: List<PowerMenuItem> =
                        listOf(
                            PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
                            PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete))
                        )
                    binding.ivFeedDetailProperty.setMenu(
                        binding.ivFeedDetailProperty,
                        postPropertyItems,
                        viewLifecycleOwner
                    )
                }
                false -> {
                    val postPropertyItems: List<PowerMenuItem> =
                        listOf(
                            PowerMenuItem(requireContext().getString(R.string.feed_post_property_report)),
                            PowerMenuItem(requireContext().getString(R.string.feed_post_property_block))
                        )
                    binding.ivFeedDetailProperty.setMenu(
                        binding.ivFeedDetailProperty,
                        postPropertyItems,
                        viewLifecycleOwner
                    )
                }
            }
        }
    }

    private fun setFeedComment() {
        binding.rvFeedDetailReply.adapter = feedCommentAdapter
        viewModel.feedComment.observe(viewLifecycleOwner) {
            feedCommentAdapter.submitList(it)
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

    private fun changeLikeBtn(button: Button, isLiked: Boolean, likeCnt: Int, likeCntText: TextView): Int {
        var isLike = isLiked
        isLike = !isLike
        button.showLike(isLike, button)

        if (isLike) {
            likeCntText.text = (likeCnt + 1).toString()
            return likeCnt + 1
        }
        else {
            likeCntText.text = (likeCnt - 1).toString()
            return likeCnt - 1
        }
    }
}