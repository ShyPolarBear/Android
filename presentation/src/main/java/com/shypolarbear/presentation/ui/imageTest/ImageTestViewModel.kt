package com.shypolarbear.presentation.ui.imageTest

import com.shypolarbear.domain.usecase.image.ImageDeleteUseCase
import com.shypolarbear.domain.usecase.image.ImageModifyUseCase
import com.shypolarbear.domain.usecase.image.ImageUploadUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageTestViewModel @Inject constructor(
    private val uploadUseCase: ImageUploadUseCase,
    private val deleteUseCase: ImageDeleteUseCase,
    private val modifyUseCase: ImageModifyUseCase
): BaseViewModel() {

}