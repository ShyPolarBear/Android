package com.beeeam.more.mypage

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.beeeam.base.BaseFragment
import com.beeeam.more.adapter.MyCommentAdapter
import com.beeeam.more.adapter.MyPostAdapter
import com.beeeam.myinfo.R
import com.beeeam.myinfo.databinding.FragmentMyPageBinding
import com.beeeam.util.Const.POWER_MENU_OFFSET_X
import com.beeeam.util.Const.POWER_MENU_OFFSET_Y
import com.beeeam.util.FeedContentType
import com.beeeam.util.PostProperty
import com.beeeam.util.PowerMenuUtil
import com.beeeam.util.createNavDeepLinkRequest
import com.beeeam.util.infiniteScroll
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint

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
                commentAdapter = MyCommentAdapter(commentFeed.content)
            }
        }

        binding.apply {
            myFeedProgressbar.isVisible = true
            tvMyPostPost.isActivated = true

            tvMyPostPost.setOnClickListener {
                showNonDataText(FeedContentType.POST, viewModel.myPostResponse.value?.count)
                invertActivation(it, tvMyPostComment)
                setAdapter(postAdapter, FeedContentType.POST)
            }

            tvMyPostComment.setOnClickListener {
                showNonDataText(FeedContentType.COMMENT, viewModel.myCommentResponse.value?.count)
                invertActivation(it, tvMyPostPost)
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
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_revise)),
                PowerMenuItem(requireContext().getString(com.beeeam.designsystem.R.string.feed_post_property_delete)),
            )

        PowerMenuUtil.getPowerMenu(
            requireContext(),
            viewLifecycleOwner,
            myPostPropertyItems,
            onItemClickListener = { position, item ->
                when (position) {
                    PostProperty.MODIFY.state -> {
                        findNavController().navigate(createNavDeepLinkRequest("shyPolarBear://fragmentFeedDetail/${feedId}"))
                    }
                    PostProperty.DELETE.state -> {
                        viewModel.requestDeleteFeed(feedId = feedId)
                    }
                }
            },
        )
            .showAsDropDown(
                view,
                POWER_MENU_OFFSET_X,
                POWER_MENU_OFFSET_Y,
            )
    }

    private fun showNonDataText(type: FeedContentType, count: Int?) {
        if (type == FeedContentType.POST) {
            binding.tvMyPostNonComment.isVisible = false
            if (count == 0 || count == null) {
                binding.tvMyPostNonPost.isVisible = true
            }
        } else {
            binding.tvMyPostNonPost.isVisible = false
            if (count == 0 || count == null) {
                binding.tvMyPostNonComment.isVisible = true
            }
        }
    }

    private fun invertActivation(onSelected: View, offSelection: View) {
        onSelected.isActivated = true
        offSelection.isActivated = false
    }
}
