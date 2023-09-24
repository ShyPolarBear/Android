package com.shypolarbear.presentation.ui.mypage

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentMyPageBinding
import com.shypolarbear.presentation.ui.mypage.adapter.MyPostAdapter
import com.shypolarbear.presentation.util.detectActivation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment :
    BaseFragment<FragmentMyPageBinding, MyPageViewModel>(R.layout.fragment_my_page) {
    override val viewModel: MyPageViewModel by viewModels()
    var isLoading = false // viewModel로 이동

    val mockList = mutableListOf<String?>(
        "1",
        "@",
        "@",
        "1",
        "@",
        "@",
        "1",
        "@",
        "@",
        "1",
        "@",
        "@",
        "1",
        "@",
        "@",
        "1",
        "@",
        "@",
    )
    override fun initView() {

        binding.apply {
            val myPostCategory = listOf(tvMyPostPost, tvMyPostComment)


            val postAdapter = MyPostAdapter.initPostAdapter(mockList)
            tvMyPostPost.isActivated = true
            myPostCategory.map { choice ->
                choice.setOnClickListener {
                    // 선택된 옵션에 따라 rv 아이템 변경
                    choice.detectActivation(*myPostCategory.filter { other ->
                        other != choice
                    }.toTypedArray())
                }
            }
            myPostBtnBack.setOnClickListener {
                findNavController().navigate(R.id.action_myPageFragment_to_navigation_more)
            }
            rvMyPost.adapter = postAdapter
            rvMyPost.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(!isLoading){
                        if(!rvMyPost.canScrollVertically(1)){
                            isLoading = true
                            lifecycleScope.launch {
                                getMoreData()
                            }
                        }
                    }
                }
            })
        }
    }

    suspend fun getMoreData() {
        delay(1000)
        mockList.add(null)
        binding.rvMyPost.adapter?.notifyItemInserted(mockList.size - 1) // diffutil써야됨
        mockList.removeAt(mockList.size - 1)
        val currentSize = mockList.size
        for(i in currentSize+1 until currentSize+10){
            mockList.add("dummy item $i")
        }
        binding.rvMyPost.adapter?.notifyDataSetChanged()
        isLoading = false
    }
}