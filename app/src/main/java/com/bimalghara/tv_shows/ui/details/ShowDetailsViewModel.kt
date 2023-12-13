package com.bimalghara.tv_shows.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bimalghara.tv_shows.common.dispatcher.DispatcherProviderSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val dispatcherProviderSource: DispatcherProviderSource
) : ViewModel() {

    private var _state =
        MutableStateFlow(DetailViewUiState())
    val state = _state.asStateFlow()

}
