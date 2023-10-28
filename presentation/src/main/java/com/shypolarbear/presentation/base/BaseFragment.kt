package com.shypolarbear.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.shypolarbear.presentation.util.onBackPressedToFinish

abstract class BaseFragment<B: ViewDataBinding, VM: BaseViewModel>(
    @LayoutRes private val layoutId: Int
): Fragment() {

    protected abstract val viewModel: VM

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner

        requireActivity().onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBackPressed()
            }
        })

        initView()
    }

    protected abstract fun initView()
    protected open fun onBackPressed() {
        onBackPressedToFinish(requireContext(), requireActivity())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}