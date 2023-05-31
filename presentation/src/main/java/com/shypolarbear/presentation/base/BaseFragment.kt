package com.shypolarbear.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<DB : ViewDataBinding, VM : BaseViewModel>(
    @LayoutRes private val layoutResId: Int,
) : Fragment() {
    // 로그용 태그
    private val TAG = this.javaClass.simpleName
    protected abstract val viewModel: VM

    private var _binding: DB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        initView()
    }

    abstract fun initView()

    //뷰 해제 해주는게 좋음
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}