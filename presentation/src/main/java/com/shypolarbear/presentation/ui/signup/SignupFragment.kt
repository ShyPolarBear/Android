package com.shypolarbear.presentation.ui.signup

import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupBinding
import com.shypolarbear.presentation.ui.signup.pages.SignupMailFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupNameFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupPhoneFragment
import com.shypolarbear.presentation.ui.signup.pages.SignupTermsFragment
import timber.log.Timber

class SignupFragment :
    BaseFragment<FragmentSignupBinding, SignupViewModel>(R.layout.fragment_signup) {

    override val viewModel: SignupViewModel by viewModels()
    private lateinit var pagerAdapter: SignupAdapter
    private lateinit var viewpager: ViewPager2
    private lateinit var indicator: TextView
    private var idx = 1

    override fun initView() {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        val isComplete = arrayListOf(false, true, true, true)

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

            viewModel.getTermData().observe(viewLifecycleOwner) { resTerms ->
                signupTvNext.isActivated = resTerms
                signupBtnNext.isActivated = resTerms
                isComplete[0] = resTerms
            }
            signupIndicator.setOnClickListener {
                // viewmodel 공유값 확인용
//                Timber.tag("Signup").d(termsNext)
            }

            signupIndicator.text = getString(R.string.signup_page_indicator, idx)
            viewpager.apply {
                adapter = pagerAdapter
                isUserInputEnabled = false
            }

            signupBtnNext.setOnClickListener {
                val currentItem = viewpager.currentItem
                signupTvNext.text = if (currentItem + 1 != 3) {
                    "다음"
                } else {
                    "가입 완료"
                }
                when (currentItem) {
                    0 -> if (isComplete[0]) {
                        goToNextPage(currentItem)
                    }

                    1 -> if (isComplete[1]) {
                        goToNextPage(currentItem)
                    }

                    2 -> if (isComplete[2]) {
                        goToNextPage(currentItem)

                    }

                    3 -> {
                        // 메인페이지로 가도록
                        if (false !in isComplete) {

                        }
                    }
                }
            }

            signupBtnBack.setOnClickListener {
                val currentItem = viewpager.currentItem
                signupTvNext.text = "다음"
                if (currentItem > 0) {
                    idx--
                    signupIndicator.text = getString(R.string.signup_page_indicator, idx)
                    signupViewpager.setCurrentItem(currentItem - 1, true)
                    Timber.d("SIGN - $currentItem")
                }
            }
        }
    }

    private fun goToNextPage(currentItem: Int) {
        if (currentItem < viewpager.adapter!!.itemCount.minus(1)) {
            idx = currentItem + 2
            indicator.text = getString(R.string.signup_page_indicator, idx)
            viewpager.setCurrentItem(currentItem + 1, true)
        }
        Timber.d("SIGN + $currentItem")
    }
}