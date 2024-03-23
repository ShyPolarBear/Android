package com.beeeam.splash

import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.beeeam.base.BaseFragment
import com.beeeam.splash.databinding.FragmentSplashBinding
import com.beeeam.util.createNavDeepLinkRequest
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
//                        findNavController().navigate(R.id.action_splashFragment_to_navigation_quiz_main)
                        findNavController().navigate(createNavDeepLinkRequest("shyPolarBear://fragmentQuizMain"))
                    }

                    Splash.FAILED.code -> {
//                        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                        findNavController().navigate(createNavDeepLinkRequest("shyPolarBear://fragmentLogin"))
                    }
                }
            }
        }
    }
}
