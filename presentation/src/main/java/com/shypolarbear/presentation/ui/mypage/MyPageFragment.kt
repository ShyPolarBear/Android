package com.shypolarbear.presentation.ui.mypage

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMyPageBinding
import com.shypolarbear.presentation.ui.mypage.adapter.MyPostAdapter
import com.shypolarbear.presentation.util.infiniteScroll
import dagger.hilt.android.AndroidEntryPoint


enum class FeedContentType(val state: Int){
    POST(0),
    COMMENT(1)
}

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModels()
    lateinit var postAdapter: Adapter<ViewHolder>

    override fun initView() {

        viewModel.myPostResponse.observe(viewLifecycleOwner){ postFeed ->
            postFeed?.let {
                binding.myFeedProgressbar.isVisible = false
                postAdapter = MyPostAdapter(postFeed.content)
                setAdapter(postAdapter, FeedContentType.POST)
            }
        }


        binding.apply {
            myFeedProgressbar.isVisible = true
            binding.tvMyPostPost.isActivated = true
            viewModel.loadMyPost()

            tvMyPostPost.setOnClickListener {
                invertActivation(it, tvMyPostComment)
                setAdapter(postAdapter, FeedContentType.POST)
            }

            tvMyPostComment.setOnClickListener {
                invertActivation(it, tvMyPostPost)
//                setAdapter(commentAdapter, FeedContentType.COMMENT)
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

    private fun invertActivation(onSelected: View, offSelection: View) {
        onSelected.isActivated = true
        offSelection.isActivated = false
    }
}