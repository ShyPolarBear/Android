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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModels()

    override fun initView() {
        binding.apply {
            tvMyPostPost.isActivated = true
            viewModel.loadMyPost()
            val postAdapter = MyPostAdapter.initPostAdapter(
                viewModel.myPostResponse.value!!.content,
                requireContext()
            )

            tvMyPostPost.setOnClickListener {
                invertActivation(tvMyPostPost, tvMyPostComment)
                setAdapter(postAdapter)

            }

            tvMyPostComment.setOnClickListener {
                invertActivation(tvMyPostComment, tvMyPostPost)
//                setAdapter(commentAdapter)
            }

            myPostBtnBack.setOnClickListener {
                findNavController().navigate(R.id.action_myPageFragment_to_navigation_more)
            }

            setAdapter(postAdapter)

            rvMyPost.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!viewModel.scrollState.value!!) {
                        if (!rvMyPost.canScrollVertically(1)) {
                            viewModel.scrollStateInverter()
                            getMoreData(postAdapter)
                        }
                    }
                }
            })
        }
    }

    private fun setAdapter(adapter: Adapter<ViewHolder>) {
        binding.rvMyPost.adapter = adapter
    }

    private fun invertActivation(onSelected: View, offSelection: View){
        onSelected.isActivated = true
        offSelection.isActivated = false
    }

    fun getMoreData(adapter: Adapter<ViewHolder>) {
        lifecycleScope.launch {
            if (!viewModel.myPostResponse.value!!.isLast) {
                val loadJob = viewModel.loadMyPost(
                    viewModel.myPostResponse.value!!.content.last().feedId
                )
                loadJob.join()
                if (adapter is MyPostAdapter) adapter.updateList(viewModel.myPostResponse.value!!.content)

                viewModel.scrollStateInverter()
            }
        }
    }
}