package com.shypolarbear.presentation.ui.signup

import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupBinding
import com.shypolarbear.presentation.ui.signup.pages.SignupMailFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupNameFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupPhoneFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupTermsFragment

val NAME_RANGE = 2..8

class SignupFragment :
    BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {
    override val viewModel: SignupViewModel by viewModels()
    private val isComplete = arrayListOf(false, false, false, false)
    private lateinit var pagerAdapter: SignupAdapter
    private lateinit var viewpager: ViewPager2
    private lateinit var indicator: TextView
    private lateinit var btnNext: View
    private lateinit var btnTv: TextView
    private var pageIndex = 1

    enum class Page(val page: Int) {
        TERMS(0),
        NAME(1),
        PHONE(2),
        MAIL(3)
    }

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
            viewpager = signupViewpager
            indicator = signupIndicator
            btnNext = signupBtnNext
            btnTv = signupTvNext

            viewModel.termData.observe(viewLifecycleOwner) { resTerms ->
                activateButtonState(resTerms, Page.TERMS.page)
            }

            viewModel.nameData.observe(viewLifecycleOwner) { resName ->
                activateButtonState(resName.length in NAME_RANGE, Page.NAME.page)
            }

            viewModel.phoneData.observe(viewLifecycleOwner) { resPhone ->
                activateButtonState(resPhone.length == 11, Page.PHONE.page)
            }

            viewModel.mailData.observe(viewLifecycleOwner) { resMail ->
                activateButtonState(resMail.isNotEmpty(), Page.MAIL.page)
            }

            signupIndicator.text = getString(R.string.signup_page_indicator, pageIndex)
            viewpager.apply {
                adapter = pagerAdapter
                isUserInputEnabled = false
            }

            signupBtnNext.setOnClickListener {
                when (val currentItem = viewpager.currentItem) {
                    Page.TERMS.page -> goToNextPage(currentItem)
                    Page.NAME.page -> goToNextPage(currentItem)
                    Page.PHONE.page -> goToNextPage(currentItem)
                    Page.MAIL.page -> {
                        if (false !in isComplete) {
                            findNavController().navigate(R.id.action_signupFragment_to_feedFragment)
                        }
                    }
                }
            }

            signupBtnBack.setOnClickListener {
                val currentItem = viewpager.currentItem
                signupTvNext.text = getString(R.string.signup_next)
                if (currentItem > 0) {
                    pageIndex--
                    signupIndicator.text = getString(R.string.signup_page_indicator, pageIndex)
                    signupViewpager.setCurrentItem(currentItem - 1, true)
                }

                when (currentItem) {
                    Page.NAME.page -> updateButtonState(isComplete[Page.TERMS.page])
                    Page.PHONE.page -> updateButtonState(isComplete[Page.NAME.page])
                    Page.MAIL.page -> updateButtonState(isComplete[Page.PHONE.page])
                }
            }
        }
    }

    private fun activateButtonState(goNextState: Boolean, pageIndex: Int) {
        btnTv.isActivated = goNextState
        btnNext.isActivated = goNextState
        isComplete[pageIndex] = goNextState
    }

    private fun updateButtonState(goNextState: Boolean) {
        btnTv.isActivated = goNextState
        btnNext.isActivated = goNextState
    }

    private fun goToNextPage(currentItem: Int) {
        if (isComplete[currentItem]) {
            if (currentItem < viewpager.adapter!!.itemCount.minus(1)) {
                pageIndex = currentItem + 2
                indicator.text = getString(R.string.signup_page_indicator, pageIndex)
                viewpager.setCurrentItem(currentItem + 1, true)
                updateButtonState(isComplete[currentItem + 1])
                if(currentItem == Page.PHONE.page) btnTv.text = getString(R.string.signup_complete)
            }
        }
    }
}