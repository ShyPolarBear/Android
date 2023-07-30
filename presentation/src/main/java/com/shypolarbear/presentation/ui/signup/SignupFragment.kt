package com.shypolarbear.presentation.ui.signup

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
        val isComplete = arrayListOf(false, false, false, false)
        var name: String = ""
        var phoneNumber: String = ""
        var mail: String = ""

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

            viewModel.getNameData().observe(viewLifecycleOwner) { resName ->
                val goNextState: Boolean = resName.length in 2..8
                name = resName
                signupTvNext.isActivated = goNextState
                signupBtnNext.isActivated = goNextState
                isComplete[1] = goNextState
            }

            viewModel.getPhoneData().observe(viewLifecycleOwner) { resPhone ->
                val goNextState: Boolean = resPhone.length == 11
                phoneNumber = resPhone
                signupTvNext.isActivated = goNextState
                signupBtnNext.isActivated = goNextState
                isComplete[2] = goNextState
            }

            viewModel.getMailData().observe(viewLifecycleOwner) { resMail ->
                val goNextState: Boolean = resMail.isNotEmpty()
                mail = resMail
                signupTvNext.isActivated = goNextState
                signupBtnNext.isActivated = goNextState
                isComplete[3] = goNextState
            }

            signupIndicator.text = getString(R.string.signup_page_indicator, idx)
            viewpager.apply {
                adapter = pagerAdapter
                isUserInputEnabled = false
            }

            signupBtnNext.setOnClickListener {
                when (val currentItem = viewpager.currentItem) {
                    0 -> if (isComplete[0]) {
                        signupTvNext.isActivated = isComplete[1]
                        signupBtnNext.isActivated = isComplete[1]
                        goToNextPage(currentItem)
                    }

                    1 -> if (isComplete[1]) {
                        signupTvNext.isActivated = isComplete[2]
                        signupBtnNext.isActivated = isComplete[2]
                        goToNextPage(currentItem)
                    }

                    2 -> if (isComplete[2]) {
                        signupTvNext.isActivated = isComplete[3]
                        signupBtnNext.isActivated = isComplete[3]
                        goToNextPage(currentItem)
                        signupTvNext.text = getString(R.string.signup_complete)
                    }

                    3 -> {
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
                    idx--
                    signupIndicator.text = getString(R.string.signup_page_indicator, idx)
                    signupViewpager.setCurrentItem(currentItem - 1, true)
                }

                when (currentItem) {
                    0 -> if (isComplete[currentItem]) {
                        signupTvNext.isActivated = isComplete[currentItem]
                        signupBtnNext.isActivated = isComplete[currentItem]
                    }

                    1 -> if (isComplete[0]) {
                        signupTvNext.isActivated = isComplete[0]
                        signupBtnNext.isActivated = isComplete[0]
                    }

                    2 -> if (isComplete[1]) {
                        signupTvNext.isActivated = isComplete[1]
                        signupBtnNext.isActivated = isComplete[1]
                    }

                    3 -> if (isComplete[2]) {
                        signupTvNext.isActivated = isComplete[2]
                        signupBtnNext.isActivated = isComplete[2]
                    }
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
    }
}