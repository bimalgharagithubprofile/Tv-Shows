package com.bimalghara.tv_shows.ui.details

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimalghara.tv_shows.BuildConfig
import com.bimalghara.tv_shows.common.dispatcher.DispatcherProviderSource
import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.domain.model.TvShows
import com.bimalghara.tv_shows.domain.use_cases.FetchSimilarShowsUseCase
import com.bimalghara.tv_shows.ui.details.DetailViewUiState.Companion.ARG_SHOW
import com.bimalghara.tv_shows.utils.wrapEspressoIdlingResource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcherProviderSource: DispatcherProviderSource,
    private val fetchSimilarShowsUseCase: FetchSimilarShowsUseCase
) : ViewModel() {
    private val logTag = "ShowDetailsViewModel"

    private var _state = MutableStateFlow(DetailViewUiState())
    val state = _state.asStateFlow()

    private var _favourite = MutableStateFlow(false)
    val favourite = _favourite.asStateFlow()

    private var _stateSimilarShows =
        MutableStateFlow<DataStateWrapper<List<TvShows>>>(DataStateWrapper.Idle())
    val stateSimilarShows = _stateSimilarShows.asStateFlow()

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

            if(stateSimilarShows.value.data.isNullOrEmpty()) loadSimilarShows(show.id)
        }
    }

    private fun loadSimilarShows(id: Int) = viewModelScope.launch(dispatcherProviderSource.io) {
        wrapEspressoIdlingResource {
            fetchSimilarShowsUseCase(id).collect {
                _stateSimilarShows.value = it
            }
        }
    }

    fun favoriteClickHandle() {
        if(_favourite.value){
            _favourite.value = false
        } else {
            _favourite.value = true
        }
    }
}
