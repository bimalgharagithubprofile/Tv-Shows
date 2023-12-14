package com.bimalghara.tv_shows.ui.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimalghara.tv_shows.common.dispatcher.DispatcherProviderSource
import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.domain.model.TvShows
import com.bimalghara.tv_shows.domain.use_cases.FetchTVShowsUseCase
import com.bimalghara.tv_shows.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val dispatcherProviderSource: DispatcherProviderSource,
    private val fetchTVShowsUseCase: FetchTVShowsUseCase
) : ViewModel() {

    private var _stateSearchActive = MutableStateFlow(false)
    val stateSearchActive = _stateSearchActive.asStateFlow()
    private var _stateSearchText = MutableStateFlow("")
    val stateSearchText = _stateSearchText.asStateFlow()

    private var _weeklyShows = MutableStateFlow<DataStateWrapper<List<TvShows>>>(DataStateWrapper.Idle())
    private var _searchedShows = MutableStateFlow<DataStateWrapper<List<TvShows>>>(DataStateWrapper.Idle())
    val state = _searchedShows
        .combine(_weeklyShows) { searchResult, weeklyResult ->
            if(stateSearchText.value.isEmpty()){
                weeklyResult
            } else {
                searchResult
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = _weeklyShows.value
        )

    fun loadData() = viewModelScope.launch(dispatcherProviderSource.io) {
        wrapEspressoIdlingResource {
            fetchTVShowsUseCase().collect {
                _weeklyShows.value = it
            }
        }
    }


    fun onActiveChange(active: Boolean) {
        _stateSearchActive.value = active
    }
    fun onQueryChange(query: String) {
        _stateSearchText.value = query
    }
    fun onSearch(query: String) = viewModelScope.launch(dispatcherProviderSource.io) {
        _stateSearchActive.value = false

        _searchedShows.value = DataStateWrapper.Loading()
        delay(3000L)
        _searchedShows.value = DataStateWrapper.Error("Test error")
        delay(3000L)
        _searchedShows.value = DataStateWrapper.Success(listOf(
            TvShows(
                id = 46198,
                name = "JP",
                overview = "A brain surgeon named Minakata Jin has spent the last two years in anguish, as his fiancee lies in a vegetative state after an operation he performed.",
                posterPath = "https://image.tmdb.org/t/p/w500/dGyyuDIDkvP2eSNgxMRrbciM1vI.jpg",
                popularity = 20.282
            )
        ))
    }

    fun clearSearch() {
        _stateSearchText.value = ""
    }
    fun closeSearch() {
        _stateSearchText.value = ""
        _searchedShows.value = DataStateWrapper.Idle()
        _stateSearchActive.value = false
    }

}
