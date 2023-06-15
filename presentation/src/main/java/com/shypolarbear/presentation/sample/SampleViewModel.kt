package com.shypolarbear.presentation.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.model.ExampleModel
import com.shypolarbear.domain.usecase.ExampleUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val exampleUseCase: ExampleUseCase
): BaseViewModel() {

    private val _sampleData = MutableLiveData<ExampleModel>()
    val sampleData: LiveData<ExampleModel> = _sampleData

    private val _sampleState: MutableStateFlow<SampleState> = MutableStateFlow(SampleState(true, false, ""))
    val sampleState: StateFlow<SampleState> = _sampleState.asStateFlow()

    fun loadSampleData() {
        viewModelScope.launch {
            val loadedSample = exampleUseCase.loadSampleData()
//            loadedSample.let {
//                _sampleData.postValue(it)
//            }
            when(loadedSample) {
                null -> {
                    _sampleState.update { state ->
                        state.copy(
                            loading = false,
                            error = true,
                        )
                    }
                }
                else -> {
                    _sampleState.update { state ->
                        state.copy(
                            loading = false,
                            category = loadedSample.toString(),
                        )
                    }
                }
            }
        }
    }
}