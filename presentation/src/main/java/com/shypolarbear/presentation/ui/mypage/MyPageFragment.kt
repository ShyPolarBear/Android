package com.shypolarbear.presentation.ui.mypage

import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
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
import com.shypolarbear.presentation.util.setMenu
import com.skydoves.powermenu.OnMenuItemClickListener
import com.skydoves.powermenu.PowerMenuItem
import dagger.hilt.android.AndroidEntryPoint


enum class FeedContentType(val state: Int) {
    POST(0),
    COMMENT(1)
}

enum class PostProperty(val state: Int) {
    MODIFY(0),
    DELETE(1)
}

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModels()
    private lateinit var postAdapter: Adapter<ViewHolder>
    private lateinit var commentAdapter: Adapter<ViewHolder>

    override fun initView() {
        viewModel.loadMyFeed()
        viewModel.myPostResponse.observe(viewLifecycleOwner) { postFeed ->
            postFeed?.let {가
                binding.myFeedProgressbar.isVisible = false
                postAdapter = MyPostAdapter(postFeed.content) { feedId: Int, view: ImageView ->
                    showMyPostPropertyMenu(view, feedId)
                }
                setAdapter(postAdapter, FeedContentType.POST)
            }
        }

        viewModel.myCommentResponse.observe(viewLifecycleOwner) { commentFeed ->
            commentFeed?.let {
                commentAdapter = MyCommentAdapter(commentFeed.content)
                setAdapter(commentAdapter, FeedContentType.COMMENT)
            }
        }

        binding.apply {
            myFeedProgressbar.isVisible = true
            binding.tvMyPostPost.isActivated = true

            tvMyPostPost.setOnClickListener {
                invertActivation(it, tvMyPostComment)
                setAdapter(postAdapter, FeedContentType.POST)
            }

            tvMyPostComment.setOnClickListener {
                invertActivation(it, tvMyPostPost)
                setAdapter(commentAdapter, FeedContentType.COMMENT)
            }

            myPostBtnBack.setOnClickListener {
                findNavController().popBackStack()
            }

        }
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

                    }
                    PostProperty.DELETE.state -> {
                        viewModel.requestDeleteFeed(feedId = feedId)
                    }
                }
            }
        )
            .showAsDropDown(
                view,
                FeedTotalFragment.POWER_MENU_OFFSET_X,
                FeedTotalFragment.POWER_MENU_OFFSET_Y
            )
    }


    private fun invertActivation(onSelected: View, offSelection: View) {
        onSelected.isActivated = true
        offSelection.isActivated = false
    }
}