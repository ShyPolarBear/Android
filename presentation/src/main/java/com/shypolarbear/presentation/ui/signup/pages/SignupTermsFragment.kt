package com.shypolarbear.presentation.ui.signup.pages

import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupTermsBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import timber.log.Timber

class SignupTermsFragment :
    BaseFragment<FragmentSignupTermsBinding, SignupViewModel>(R.layout.fragment_signup_terms) {

    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })
    enum class TERMS(val position: Int) {
        PRIVACY(0),
        TERM(1),
        AGE(2)
    }
    private val isComplete = BooleanArray(TERMS.values().size) { false }
    private lateinit var cbAll : CheckBox
    private var allCheck = false
    override fun initView() {
        binding.apply {
            cbAll = signupTermsCbAll
            val checkList = listOf(signupTermsCbTerms, signupTermsCbPrivacy, signupTermsCbAge)

            signupTermsCbAll.setOnClickListener {
                allCheck = !allCheck
                checkAll(checkList)
            }

            signupTermsCbAll.setOnCheckedChangeListener { buttonView, isChecked ->
                buttonView.isChecked = isComplete.all { it }
            }

            signupTermsCbPrivacy.setOnCheckedChangeListener { buttonView, isChecked ->
                checkEachItem(isChecked, TERMS.PRIVACY.position)
            }

            signupTermsCbTerms.setOnCheckedChangeListener { buttonView, isChecked ->
                checkEachItem(isChecked, TERMS.TERM.position)
            }

            signupTermsCbAge.setOnCheckedChangeListener { buttonView, isChecked ->
                checkEachItem(isChecked, TERMS.AGE.position)
            }
        }
    }
    private fun checkAll(checkList: List<CheckBox>) {
        for (checkBox in checkList) {
            checkBox.isChecked = allCheck
        }
        viewModel.setTermData(allCheck)
    }

    private fun checkEachItem(checkState: Boolean, position: Int){
        if (checkState) {
            isComplete[position] = true
            if (false !in isComplete) {
                cbAll.isChecked = true
                viewModel.setTermData(true)
            }
        } else {
            isComplete[position] = false
            cbAll.isChecked = false
            viewModel.setTermData(false)
        }
    }
}