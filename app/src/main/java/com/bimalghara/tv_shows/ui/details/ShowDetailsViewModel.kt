package com.bimalghara.tv_shows.ui.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimalghara.tv_shows.BuildConfig
import com.bimalghara.tv_shows.common.dispatcher.DispatcherProviderSource
import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.domain.model.TvShowDetails
import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.bimalghara.tv_shows.domain.use_cases.FavouriteTVShowsUseCase
import com.bimalghara.tv_shows.domain.use_cases.FetchSimilarTvShowsUseCase
import com.bimalghara.tv_shows.domain.use_cases.FetchTvShowDetailsUseCase
import com.bimalghara.tv_shows.ui.details.DetailViewUiState.Companion.ARG_SHOW
import com.bimalghara.tv_shows.ui.details.DetailViewUiState.Companion.ARG_SIMILAR_SHOW
import com.bimalghara.tv_shows.utils.wrapEspressoIdlingResource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcherProviderSource: DispatcherProviderSource,
    private val fetchTvShowDetailsUseCase: FetchTvShowDetailsUseCase,
    private val favouriteTVShowsUseCase: FavouriteTVShowsUseCase,
    private val fetchSimilarTvShowsUseCase: FetchSimilarTvShowsUseCase
) : ViewModel() {
    private val logTag = "ShowDetailsViewModel"

    private var _state = MutableStateFlow(DetailViewUiState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _state.value
    )

    private var _stateDetails =
        MutableStateFlow<DataStateWrapper<TvShowDetails>>(DataStateWrapper.Idle())
    val stateDetails = _stateDetails.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _stateDetails.value
    )

    private var _favourite = MutableStateFlow(false)
    val favourite = _favourite.asStateFlow()

    private var _stateSimilarShows =
        MutableStateFlow<DataStateWrapper<List<TvShowsEntity>>>(DataStateWrapper.Idle())
    val stateSimilarShows = _stateSimilarShows.asStateFlow()

    init {
        val gson = Gson()
        val encodedShowStr = savedStateHandle.get<String>(ARG_SHOW)
        val isSimilarShow = savedStateHandle.get<Boolean>(ARG_SIMILAR_SHOW) ?: false
        if (encodedShowStr != null) {
            val decodedShowStr =
                URLDecoder.decode(encodedShowStr, StandardCharsets.UTF_8.toString())
            val show = gson.fromJson(decodedShowStr, TvShowsEntity::class.java)
            _state.value = _state.value.copy(
                show = show,
                isSimilarShow = isSimilarShow
            )
            if (BuildConfig.DEBUG) Log.d(logTag, "state::${state.value}")

            _favourite.value = show.isFavourite

            if (stateDetails.value.data == null) loadShowDetails(show.id)
            if (stateSimilarShows.value.data.isNullOrEmpty()) loadSimilarShows(show.id)
        }
    }

    private fun loadShowDetails(id: Int) = viewModelScope.launch(dispatcherProviderSource.io) {
        _stateDetails.value = DataStateWrapper.Loading()
        wrapEspressoIdlingResource {
            fetchTvShowDetailsUseCase(id).collect {
                _stateDetails.value = it
            }
        }
    }

    private fun loadSimilarShows(id: Int) = viewModelScope.launch(dispatcherProviderSource.io) {
        wrapEspressoIdlingResource {
            fetchSimilarTvShowsUseCase(id).collect {
                _stateSimilarShows.value = it
            }
        }
    }

    fun favoriteClickHandle() = viewModelScope.launch(dispatcherProviderSource.io) {
        state.value.show?.let {
            wrapEspressoIdlingResource {
                if (it.isFavourite) {
                    val updatedShow = favouriteTVShowsUseCase(it, false)
                    if (updatedShow != null) {
                        _state.value = _state.value.copy(show = updatedShow)
                        _favourite.value = updatedShow.isFavourite
                    }
                } else {
                    val updatedShow = favouriteTVShowsUseCase(it, true)
                    if (updatedShow != null) {
                        _state.value = _state.value.copy(show = updatedShow)
                        _favourite.value = updatedShow.isFavourite
                    }
                }
            }
        }
    }
}
