package com.shypolarbear.presentation.ui.imageTest

import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.image.ImageFiles
import com.shypolarbear.domain.model.image.ImageModifyRequest
import com.shypolarbear.domain.model.image.ImageUploadRequest
import com.shypolarbear.domain.model.image.ImageUrls
import com.shypolarbear.domain.usecase.image.ImageDeleteUseCase
import com.shypolarbear.domain.usecase.image.ImageModifyUseCase
import com.shypolarbear.domain.usecase.image.ImageUploadUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import com.shypolarbear.presentation.util.Event
import com.shypolarbear.presentation.util.simpleHttpErrorCheck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ImageTestViewModel @Inject constructor(
    private val uploadUseCase: ImageUploadUseCase,
    private val deleteUseCase: ImageDeleteUseCase,
    private val modifyUseCase: ImageModifyUseCase,
) : BaseViewModel() {

    fun requestUpload(type: String, imageFiles: List<File>) {
        viewModelScope.launch {
            val responseUpload = uploadUseCase(ImageUploadRequest(type, imageFiles))

            responseUpload.onSuccess { response ->
                Timber.tag("IMG").d("${response.data}")
            }
                .onFailure { error ->
                    Timber.tag("IMG").d("${error.message}")

                    simpleHttpErrorCheck(error)
                }
        }
    }

    fun requestDelete(imageUrls: List<String>) {
        viewModelScope.launch {
            val responseDelete = deleteUseCase(imageUrls)

            responseDelete.onSuccess { response ->
                Timber.tag("IMG").d("${response.data}")
            }
                .onFailure { error ->
                    Timber.tag("IMG").d("${error}")

                    simpleHttpErrorCheck(error)
                }
        }
    }

    fun requestModify(type: String, newImageFiles: List<File>, oldImageFiles: List<String>) {
        if(newImageFiles.size == oldImageFiles.size){
            viewModelScope.launch {

                val responseUpload =
                    modifyUseCase(ImageModifyRequest(type, newImageFiles, oldImageFiles))

                responseUpload.onSuccess { response ->
                    Timber.tag("IMG").d("${response.data}")
                }
                    .onFailure { error ->
                        Timber.tag("IMG").d("${error.message}")

                        simpleHttpErrorCheck(error)
                    }
            }
        }
    }

}