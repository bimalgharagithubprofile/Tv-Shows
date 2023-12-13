package com.bimalghara.tv_shows.ui.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimalghara.tv_shows.common.dispatcher.DispatcherProviderSource
import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.domain.model.TvShows
import com.bimalghara.tv_shows.domain.use_cases.FetchTVShowsUseCase
import com.bimalghara.tv_shows.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val dispatcherProviderSource: DispatcherProviderSource,
    private val fetchTVShowsUseCase: FetchTVShowsUseCase
) : ViewModel() {

    private var _state =
        MutableStateFlow<DataStateWrapper<List<TvShows>>>(DataStateWrapper.Idle())
    val state = _state.asStateFlow()

    fun loadData() = viewModelScope.launch(dispatcherProviderSource.io) {
        wrapEspressoIdlingResource {
            fetchTVShowsUseCase().collect {
                _state.value = it
            }
        }
    }
}
