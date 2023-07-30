package com.shypolarbear.presentation.ui.signup.pages

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import androidx.fragment.app.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseFragment
import com.shypolarbear.presentation.databinding.FragmentSignupMailBinding
import com.shypolarbear.presentation.ui.signup.SignupViewModel
import com.shypolarbear.presentation.util.hideKeyboard

class SignupMailFragment:BaseFragment<FragmentSignupMailBinding, SignupViewModel>(R.layout.fragment_signup_mail) {
    override val viewModel: SignupViewModel by viewModels({ requireParentFragment() })
    val emailPattern = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")
    override fun initView() {
        binding.apply {
            etSignupMail.setOnEditorActionListener{ v, _, event ->
                if (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                    // 키보드 숨기기
                    hideKeyboard()
                    v.clearFocus()
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }

            etSignupMail.addTextChangedListener ( object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    val match = emailPattern.find(s.toString())
                    if (match != null) {
                        sendData(match.value)
                    } else {
                        sendData("")
                    }
                }

            })

        }
    }
    private fun sendData(data: String) {
        viewModel.setMailData(data)
    }
}