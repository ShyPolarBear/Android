package com.shypolarbear.presentation.sample

import androidx.lifecycle.viewModelScope
import com.shypolarbear.domain.usecase.ExampleUseCase
import com.shypolarbear.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    private val exampleUseCase: ExampleUseCase
): BaseViewModel() {

    private val _sampleState: MutableStateFlow<SampleState> = MutableStateFlow(SampleState(true, false, ""))
    val sampleState: StateFlow<SampleState> = _sampleState.asStateFlow()

    fun loadSampleData() {
        viewModelScope.launch {
            val loadedSample = exampleUseCase.loadSampleData()
            _sampleState.update { state -> state.copy(loading = true) }

            loadedSample
                .onSuccess {
                    _sampleState.update { state ->
                        state.copy(
                            loading = false,
                            category = it.toString()
                        )
                    }
                }
                .onFailure {
                    _sampleState.update { state ->
                        state.copy(
                            loading = false,
                            error = true,
                        )
                    }
                }
        }
    }
}