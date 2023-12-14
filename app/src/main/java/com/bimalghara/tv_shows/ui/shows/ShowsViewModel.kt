package com.bimalghara.tv_shows.ui.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bimalghara.tv_shows.common.dispatcher.DispatcherProviderSource
import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.bimalghara.tv_shows.domain.use_cases.DownloadTVShowsUseCase
import com.bimalghara.tv_shows.domain.use_cases.GetTVShowsUseCase
import com.bimalghara.tv_shows.domain.use_cases.SearchTVShowsUseCase
import com.bimalghara.tv_shows.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    private val dispatcherProviderSource: DispatcherProviderSource,
    private val downloadTVShowsUseCase: DownloadTVShowsUseCase,
    private val getTVShowsUseCase: GetTVShowsUseCase,
    private val searchTVShowsUseCase: SearchTVShowsUseCase
) : ViewModel() {

    private var _stateSearchActive = MutableStateFlow(false)
    val stateSearchActive = _stateSearchActive.asStateFlow()
    private var _stateSearchText = MutableStateFlow("")
    val stateSearchText = _stateSearchText.asStateFlow()

    private var _weeklyShows = MutableStateFlow<DataStateWrapper<List<TvShowsEntity>>>(DataStateWrapper.Idle())
    private var _searchedShows = MutableStateFlow<DataStateWrapper<List<TvShowsEntity>>>(DataStateWrapper.Idle())
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

    init {
        loadData()
    }

    fun downloadData() = viewModelScope.launch(dispatcherProviderSource.io) {
        _weeklyShows.value = DataStateWrapper.Loading()
        wrapEspressoIdlingResource {
            try {
                downloadTVShowsUseCase()
            }catch (e: Exception){
                _weeklyShows.value = DataStateWrapper.Error(e.localizedMessage)
            }
        }
    }

    private fun loadData() = viewModelScope.launch {
        wrapEspressoIdlingResource {
            getTVShowsUseCase().collect {
                if(it.isNotEmpty()) {
                    _weeklyShows.value = DataStateWrapper.Success(it)
                }
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

        wrapEspressoIdlingResource {
            searchTVShowsUseCase(query).collect {
                _searchedShows.value = it
            }
        }
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
