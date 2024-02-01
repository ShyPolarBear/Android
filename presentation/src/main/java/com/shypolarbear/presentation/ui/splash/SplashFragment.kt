package com.shypolarbear.presentation.ui.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

enum class Splash(val code: Int) {
    SUCCESS(0),
    FAILED(401),
}

@AndroidEntryPoint
class SplashFragment :
    BaseFragment<FragmentSplashBinding, SplashViewModel>(R.layout.fragment_splash) {
    override val viewModel: SplashViewModel by viewModels()
    override fun initView() {
        viewModel.returnCode.observe(viewLifecycleOwner) { code ->
            code?.let {
                when (code) {
                    Splash.SUCCESS.code -> {
                        findNavController().navigate(R.id.action_splashFragment_to_navigation_quiz_main)
                    }

                    Splash.FAILED.code -> {
                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    }
                }
            }
        }
    }
}
