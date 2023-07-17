package com.shypolarbear.presentation.ui.feed.feedTotal

import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentFeedTotalBinding
import com.shypolarbear.presentation.ui.feed.adapter.FeedPostAdapter
import com.shypolarbear.presentation.util.checkLike
import com.shypolarbear.presentation.util.setMenu
import com.skydoves.powermenu.PowerMenuItem

class FeedTotalFragment: BaseFragment<FragmentFeedTotalBinding, FeedTotalViewModel> (
    R.layout.fragment_feed_total
){

    companion object {
        const val POWER_MENU_OFFSET_X = -290
        const val POWER_MENU_OFFSET_Y = 0
    }

    override val viewModel: FeedTotalViewModel by viewModels()
    private var isLike = false
    private val feedSortItems: List<PowerMenuItem> by lazy {
        listOf(
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_recent_best)),
            PowerMenuItem(requireContext().getString(R.string.feed_post_property_best))
        )
    }

    override fun initView() {

        binding.ivFeedToolbarSort.setOnClickListener {
            binding.ivFeedToolbarSort.setMenu(
                binding.ivFeedToolbarSort,
                feedSortItems,
                viewLifecycleOwner
            )
        }

        viewModel.loadFeedPost()
        setFeedPost()
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
            onBtnLikeClick = { btn: Button ->
                changeLikeBtn(btn)
            },
            onMoveToDetailClick = {
                showFeedPostDetail()
            }
        )
        binding.rvFeedPost.adapter = feedPostAdapter
        viewModel.feedPost.observe(viewLifecycleOwner) {
            feedPostAdapter.submitList(it)
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

    private fun changeLikeBtn(button: Button) {
        isLike = !isLike
        button.checkLike(isLike, button)
    }

    private fun showFeedPostDetail() {
        findNavController().navigate(R.id.action_feedTotalFragment_to_feedDetailFragment)
    }
}