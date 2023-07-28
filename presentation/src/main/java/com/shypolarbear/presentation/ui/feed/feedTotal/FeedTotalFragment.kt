package com.shypolarbear.presentation.ui.feed.feedTotal

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedTotalBinding
import com.shypolarbear.presentation.ui.feed.feedTotal.adapter.FeedPostAdapter
import com.shypolarbear.presentation.util.showLike
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
        const val FEED_ID = "feed_id"
    }

    override val viewModel: FeedTotalViewModel by viewModels()
    private var isLike = false
    private val feedPostAdapter = FeedPostAdapter(
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
        onBtnLikeClick = { btn: Button, isLiked: Boolean, likeCnt: Int, textView: TextView ->
            changeLikeBtn(btn, isLiked, likeCnt, textView)
        },
        onMoveToDetailClick = { feedId: Int -> showFeedPostDetail(feedId) }
    )
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
        binding.rvFeedPost.adapter = feedPostAdapter
        lifecycleScope.launch {
            viewModel.feed.observe(viewLifecycleOwner) {
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

    private fun showFeedPostDetail(feedId: Int) {
        Timber.d(feedId.toString())
        findNavController().navigate(R.id.action_feedTotalFragment_to_feedDetailFragment, bundleOf(
            FEED_ID to feedId.toString()
        ))
    }
}