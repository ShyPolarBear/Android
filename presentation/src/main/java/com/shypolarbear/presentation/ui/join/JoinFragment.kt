package com.shypolarbear.presentation.ui.join

import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupBinding
import com.shypolarbear.presentation.ui.join.pages.JoinMailFragment
import com.shypolarbear.presentation.ui.join.pages.JoinNameFragment
import com.shypolarbear.presentation.ui.join.pages.JoinPhoneFragment
import com.shypolarbear.presentation.ui.join.pages.JoinTermsFragment
import com.shypolarbear.presentation.util.ACCESS_TOKEN
import com.shypolarbear.presentation.util.dataStore
import com.shypolarbear.presentation.util.setTokens
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber

val NAME_RANGE = 2..8

enum class Page(val page: Int) {
    TERMS(0),
    NAME(1),
    PHONE(2),
    MAIL(3)
}

@AndroidEntryPoint
class JoinFragment :
    BaseFragment<FragmentSignupBinding, JoinViewModel>(R.layout.fragment_signup) {
    override val viewModel: JoinViewModel by viewModels()
    private lateinit var pagerAdapter: JoinAdapter

    override fun initView() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        val userAccessToken: Flow<String?> = requireContext().dataStore.data.map {
            it[ACCESS_TOKEN]
        }
        runBlocking {
            userAccessToken.collect { value ->
                Timber.tag("DATASTORE").d(
                    "${
                        value
                    }"
                )
            }
        }

        val pageList = listOf(
            JoinTermsFragment(),
            JoinNameFragment(),
            JoinPhoneFragment(),
            JoinMailFragment()
        )
        pagerAdapter = JoinAdapter(this, pageList)
        binding.apply {
            viewModel.termData.observe(viewLifecycleOwner) { resTerms ->

                if (viewModel.getActualPageIndex() == Page.TERMS.page) {
                    activateButtonState(resTerms, Page.TERMS.page)
                }
            }

            viewModel.nameData.observe(viewLifecycleOwner) { resName ->
                if (viewModel.getActualPageIndex() == Page.NAME.page) {
                    activateButtonState(resName.length in NAME_RANGE, Page.NAME.page)
                }
            }

            viewModel.phoneData.observe(viewLifecycleOwner) { resPhone ->
                if (viewModel.getActualPageIndex() == Page.PHONE.page) {
                    activateButtonState(resPhone.length == 11, Page.PHONE.page)
                }
            }

            viewModel.mailData.observe(viewLifecycleOwner) { resMail ->
                if (viewModel.getActualPageIndex() == Page.MAIL.page) {
                    activateButtonState(resMail.isNotEmpty(), Page.MAIL.page)
                }
            }

            viewModel.tokens.observe(viewLifecycleOwner) { tokens ->
                tokens?.let {
                    lifecycleScope.launch {
                        setTokens(requireContext(), viewModel.tokens.value!!)
                        Timber.tag("DATASTORE").d("$userAccessToken")
                        findNavController().navigate(R.id.action_signupFragment_to_feedTotalFragment)
                    }
                }
            }

            viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
                message?.let {
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                }
            }

            signupIndicator.text =
                getString(R.string.signup_page_indicator, viewModel.pageIndex.value)
            updateButtonState(viewModel.pageState[viewModel.pageIndex.value!! - 1])

            signupViewpager.apply {
                adapter = pagerAdapter
                isUserInputEnabled = false
            }

            signupBtnNext.setOnClickListener {
                when (val currentItem = signupViewpager.currentItem) {
                    Page.TERMS.page, Page.NAME.page, Page.PHONE.page -> {
                        goToNextPage(currentItem)
                    }

                    Page.MAIL.page -> {
                        if (viewModel.pageState.all { it }) {
                            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                                if (error != null) {
                                    Timber.tag("JOIN").e(error, "카카오계정으로 로그인 실패")
                                } else if (token != null) {
                                    Timber.tag("JOIN").i("카카오계정으로 로그인 성공 " + token.accessToken)
                                    viewModel.requestJoin(token.accessToken)
                                }
                            }
                            if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
                                UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                                    if (error != null) {
                                        Timber.tag("JOIN").e(error, "카카오톡으로 로그인 실패")
                                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                                            return@loginWithKakaoTalk
                                        }
                                        // 연결된 계정 없을 때
                                        UserApiClient.instance.loginWithKakaoAccount(
                                            requireContext(),
                                            callback = callback
                                        )
                                    } else if (token != null) {
                                        Timber.tag("JOIN").i("카카오톡으로 로그인 성공 " + token.accessToken)
                                        viewModel.requestJoin(token.accessToken)
                                    }
                                }
                            } else {
                                UserApiClient.instance.loginWithKakaoAccount(
                                    requireContext(),
                                    callback = callback
                                )
                            }
                        }
                    }
                }
            }

            signupBtnBack.setOnClickListener {
                val currentItem = signupViewpager.currentItem
                signupTvNext.text = getString(R.string.signup_next)
                if (currentItem > 0) {
                    viewModel.goBackPageIndex()
                    signupIndicator.text =
                        getString(R.string.signup_page_indicator, viewModel.pageIndex.value)
                    signupViewpager.setCurrentItem(currentItem - 1, true)
                }

                when (currentItem) {
                    Page.NAME.page -> updateButtonState(viewModel.pageState[Page.TERMS.page])
                    Page.PHONE.page -> updateButtonState(viewModel.pageState[Page.NAME.page])
                    Page.MAIL.page -> updateButtonState(viewModel.pageState[Page.PHONE.page])
                }
            }
        }
    }

    private fun activateButtonState(goNextState: Boolean, pageIndex: Int) {
        binding.signupTvNext.isActivated = goNextState
        binding.signupBtnNext.isActivated = goNextState
        viewModel.setPageState(pageIndex, goNextState)
    }

    private fun updateButtonState(goNextState: Boolean) {
        binding.signupTvNext.isActivated = goNextState
        binding.signupBtnNext.isActivated = goNextState
        Timber.tag("restore").d("${viewModel.pageState} View: ${binding.signupBtnNext.isActivated}")
    }

    private fun goToNextPage(currentItem: Int) {
        if (viewModel.pageState[currentItem]) {
            if (currentItem < binding.signupViewpager.adapter!!.itemCount.minus(1)) {
                viewModel.goNextPageIndex()
                binding.signupIndicator.text =
                    getString(R.string.signup_page_indicator, viewModel.pageIndex.value)
                binding.signupViewpager.setCurrentItem(currentItem + 1, true)
                updateButtonState(viewModel.pageState[currentItem + 1])
                if (currentItem == Page.PHONE.page) binding.signupTvNext.text =
                    getString(R.string.signup_complete)
            }
        }
    }


}