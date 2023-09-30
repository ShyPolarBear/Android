package com.shypolarbear.presentation.ui.mypage

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMyPageBinding
import com.shypolarbear.presentation.ui.mypage.adapter.MyPostAdapter
import com.shypolarbear.presentation.util.infiniteScroll
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


enum class FeedContentType(val state: Int){
    POST(0),
    COMMENT(1)
}

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModels()

    override fun initView() {
        binding.apply {
            val postAdapter = MyPostAdapter(viewModel.myPostResponse.value!!.content)
            initAdapter(postAdapter)

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

    private fun initAdapter(adapter: Adapter<ViewHolder>){
        binding.tvMyPostPost.isActivated = true
        viewModel.loadMyPost()
        setAdapter(adapter, FeedContentType.POST)
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