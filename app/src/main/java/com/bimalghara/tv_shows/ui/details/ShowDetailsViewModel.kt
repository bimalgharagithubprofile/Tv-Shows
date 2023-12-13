package com.bimalghara.tv_shows.ui.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bimalghara.tv_shows.BuildConfig
import com.bimalghara.tv_shows.common.dispatcher.DispatcherProviderSource
import com.bimalghara.tv_shows.domain.model.TvShows
import com.bimalghara.tv_shows.ui.details.DetailViewUiState.Companion.ARG_SHOW
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcherProviderSource: DispatcherProviderSource
) : ViewModel() {
    private val logTag = "ShowDetailsViewModel"

    private var _state = MutableStateFlow(DetailViewUiState())
    val state = _state.asStateFlow()

    init {
        val gson = Gson()
        val encodedShowStr = savedStateHandle.get<String>(ARG_SHOW)
        if(encodedShowStr != null){
            val decodedShowStr = URLDecoder.decode(encodedShowStr, StandardCharsets.UTF_8.toString())
            val show = gson.fromJson(decodedShowStr, TvShows::class.java)
            _state.value = _state.value.copy(
                show = show
            )
            if(BuildConfig.DEBUG) Log.d(logTag, "state::${state.value}")
        }
    }
}
