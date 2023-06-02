package com.shypolarbear.presentation.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shypolarbear.domain.model.ExampleModel
import com.shypolarbear.domain.usecase.ExampleUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@HiltViewModel
class TestViewModel @Inject constructor(
    private val exampleUseCase: ExampleUseCase
)
    : BaseViewModel(){
    private val _sampleData = MutableLiveData<ExampleModel>()
    val sampleData: LiveData<ExampleModel> = _sampleData

    init {
        GlobalScope.launch {
            loadSampleData()
        }
    }

    suspend fun loadSampleData() {
        val loadedSample = exampleUseCase.loadSampleData()
        loadedSample.let {
            _sampleData.postValue(it)
        }
    }
}