package com.shypolarbear.presentation.ui.login

import androidx.activity.viewModels
import com.shypolarbear.presentation.R
import com.shypolarbear.presentation.base.BaseActivity
import com.shypolarbear.presentation.databinding.ActivityAuthCodeHandlerBinding

class AuthCodeHandlerActivity: BaseActivity<ActivityAuthCodeHandlerBinding, LoginViewModel>(R.layout.activity_auth_code_handler) {
    override val viewModel: LoginViewModel by viewModels()

    override fun initView() {

    }

    override fun preLoad() {

    }
}