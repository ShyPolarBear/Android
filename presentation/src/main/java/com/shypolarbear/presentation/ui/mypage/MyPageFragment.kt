package com.shypolarbear.presentation.ui.mypage

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shypolarbear.domain.model.mypage.MyFeed
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMyPageBinding
import com.shypolarbear.presentation.ui.mypage.adapter.MyPostAdapter
import com.shypolarbear.presentation.util.detectActivation
import com.shypolarbear.presentation.util.invertActivation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.lang3.ObjectUtils.Null

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModels()
    var isLoading = false // viewModel로 이동

    val mockList = mutableListOf<MyFeed?>(
        MyFeed(
            2,
            "환경을 지키자2",
            ""
        ),
        MyFeed(
            3,
            "환경을 지키자2",
            ""
        ),
        MyFeed(
            4,
            "환경을 지키자2",
            ""
        ),
        MyFeed(
            5,
            "환경을 지키자2",
            ""
        ),
        MyFeed(
            6,
            "환경을 지키자2",
            ""
        ),
        MyFeed(
            7,
            "환경을 지키자2",
            ""
        ),
    )

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
                setAdapter(postAdapter)

            }

            myPostBtnBack.setOnClickListener {
                findNavController().navigate(R.id.action_myPageFragment_to_navigation_more)
            }

            setAdapter(postAdapter)

            rvMyPost.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!isLoading) {
                        if (!rvMyPost.canScrollVertically(1)) {
                            isLoading = true
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

    fun getMoreData(adapter: Adapter<ViewHolder>) {
        lifecycleScope.launch {
            if (!viewModel.myPostResponse.value!!.isLast) {
                val loadJob = viewModel.loadMyPost(
                    viewModel.myPostResponse.value!!.content.last().feedId
                )
                loadJob.join()
                if (adapter is MyPostAdapter) adapter.updateList(viewModel.myPostResponse.value!!.content)
            }
        }

        isLoading = false
    }
}