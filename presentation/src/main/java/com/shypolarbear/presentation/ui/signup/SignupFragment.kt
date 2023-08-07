package com.shypolarbear.presentation.ui.signup

import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupBinding
import com.shypolarbear.presentation.ui.signup.pages.SignupMailFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupNameFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupPhoneFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupTermsFragment
import timber.log.Timber

val NAME_RANGE = 2..8
enum class Page(val page: Int) {
    TERMS(0),
    NAME(1),
    PHONE(2),
    MAIL(3)
}

class SignupFragment :
    BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {
    override val viewModel: SignupViewModel by viewModels()
    private lateinit var pagerAdapter: SignupAdapter

    override fun initView() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        val pageList = listOf(
            SignupTermsFragment(),
            SignupNameFragment(),
            SignupPhoneFragment(),
            SignupMailFragment()
        )
        pagerAdapter = SignupAdapter(this, pageList)
        binding.apply {
            viewModel.termData.observe(viewLifecycleOwner) { resTerms->
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

            signupIndicator.text = getString(R.string.signup_page_indicator, viewModel.pageIndex.value)
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
                            findNavController().navigate(R.id.action_signupFragment_to_feedFragment)
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