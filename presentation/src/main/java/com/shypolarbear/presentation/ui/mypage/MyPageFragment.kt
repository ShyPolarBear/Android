package com.shypolarbear.presentation.ui.mypage

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMyPageBinding
import com.shypolarbear.presentation.ui.feed.feedTotal.FeedTotalFragment
import com.shypolarbear.presentation.ui.mypage.adapter.MyCommentAdapter
import com.shypolarbear.presentation.ui.mypage.adapter.MyPostAdapter
import com.shypolarbear.presentation.util.PowerMenuUtil
import com.shypolarbear.presentation.util.infiniteScroll
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint

enum class FeedContentType(val state: Int) {
    POST(0),
    COMMENT(1),
}

enum class PostProperty(val state: Int) {
    MODIFY(0),
    DELETE(1),
}

enum class MyPostTabItem {
    POST,
    COMMENT,
}

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModels()
    private lateinit var postAdapter: Adapter<ViewHolder>
    private lateinit var commentAdapter: Adapter<ViewHolder>
    private lateinit var myFeedToDetail: NavDirections

    override fun initView() {
        viewModel.loadMyFeed()
        viewModel.myPostResponse.observe(viewLifecycleOwner) { postFeed ->
            postFeed?.let {
                binding.myFeedProgressbar.isVisible = false
                if (it.count != 0) {
                    binding.tvMyPostNonPost.isVisible = false
                }
                postAdapter = MyPostAdapter(postFeed.content) { feedId: Int, view: ImageView ->
                    showMyPostPropertyMenu(view, feedId)
                }
                setAdapter(postAdapter, FeedContentType.POST)
            }
        }

        viewModel.myCommentResponse.observe(viewLifecycleOwner) { commentFeed ->
            commentFeed?.let {
                if (it.count != 0) {
                    binding.tvMyPostNonComment.isVisible = false
                }
                commentAdapter = MyCommentAdapter(commentFeed.content)
            }
        }

        binding.apply {
            myFeedProgressbar.isVisible = true
            tvMyPostPost.isActivated = true

            tvMyPostPost.setOnClickListener {
                invertActivation(it, tvMyPostComment)
                showNonDataText(MyPostTabItem.POST)
                setAdapter(postAdapter, FeedContentType.POST)
            }

            tvMyPostComment.setOnClickListener {
                invertActivation(it, tvMyPostPost)
                showNonDataText(MyPostTabItem.COMMENT)
                setAdapter(commentAdapter, FeedContentType.COMMENT)
            }

            myPostBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onBackPressed() {
        findNavController().popBackStack()
    }

    private fun setAdapter(adapter: Adapter<ViewHolder>, contentType: FeedContentType) {
        binding.rvMyPost.adapter = adapter
        binding.rvMyPost.infiniteScroll {
            viewModel.loadMoreMyPost(contentType)
        }
    }

    private fun showMyPostPropertyMenu(view: ImageView, feedId: Int) {
        val myPostPropertyItems: List<PowerMenuItem> =
            listOf(
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(R.string.feed_post_property_delete)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myPostPropertyItems,
            onItemClickListener = { position, item ->
                when (position) {
                    PostProperty.MODIFY.state -> {
                        myFeedToDetail = MyPageFragmentDirections.actionMyPageFragmentToFeedDetailFragment(feedId)
                        findNavController().navigate(myFeedToDetail)
                    }
                    PostProperty.DELETE.state -> {
                        viewModel.requestDeleteFeed(feedId = feedId)
                    }
                }
            },
        )
            .showAsDropDown(
                view,
                FeedTotalFragment.POWER_MENU_OFFSET_X,
                FeedTotalFragment.POWER_MENU_OFFSET_Y,
            )
    }

    private fun showNonDataText(type: MyPostTabItem) {
        if (type == MyPostTabItem.POST) {
            binding.tvMyPostNonPost.isVisible = true
            binding.tvMyPostNonComment.isVisible = false
        } else {
            binding.tvMyPostNonPost.isVisible = false
            binding.tvMyPostNonComment.isVisible = true
        }
    }

    private fun invertActivation(onSelected: View, offSelection: View) {
        onSelected.isActivated = true
        offSelection.isActivated = false
    }
}
