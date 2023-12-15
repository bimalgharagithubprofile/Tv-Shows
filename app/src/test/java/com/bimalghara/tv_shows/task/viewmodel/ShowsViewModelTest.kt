package com.bimalghara.tv_shows.task.viewmodel


import app.cash.turbine.test
import com.bimalghara.tv_shows.common.dispatcher.TestDispatcherProvider
import com.bimalghara.tv_shows.data.repository.TVShowsRepositoryImpl
import com.bimalghara.tv_shows.domain.model.DataStateWrapper
import com.bimalghara.tv_shows.domain.model.TvShowsEntity
import com.bimalghara.tv_shows.domain.use_cases.DownloadTVShowsUseCase
import com.bimalghara.tv_shows.domain.use_cases.GetTVShowsUseCase
import com.bimalghara.tv_shows.domain.use_cases.SearchTVShowsUseCase
import com.bimalghara.tv_shows.ui.shows.ShowsViewModel
import com.bimalghara.tv_shows.utils.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ShowsViewModelTest {

    private val testDispatcher = TestDispatcherProvider()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(testDispatcher.main)


    private  val tvShowsRepository: TVShowsRepositoryImpl =  mockk()
    private lateinit var downloadTVShowsUseCase: DownloadTVShowsUseCase
    private lateinit var getTVShowsUseCase: GetTVShowsUseCase
    private lateinit var searchTVShowsUseCase: SearchTVShowsUseCase
    private lateinit var viewModel: ShowsViewModel

    @Before
    fun setUp(){
        downloadTVShowsUseCase = DownloadTVShowsUseCase(tvShowsRepository)
        getTVShowsUseCase = GetTVShowsUseCase(tvShowsRepository)
        searchTVShowsUseCase = SearchTVShowsUseCase(tvShowsRepository)
        viewModel = ShowsViewModel(testDispatcher, downloadTVShowsUseCase, getTVShowsUseCase, searchTVShowsUseCase)
    }

    @Test
    fun `Final TvShows state expected Idle`() = runTest {

        //Assert
        viewModel.state.test {
            val result = expectMostRecentItem()

            assertThat(result).isInstanceOf(DataStateWrapper.Idle::class.java)
            assertThat(result.data).isNull()

            cancel()
        }
    }

    @Test
    fun `Weekly TvShows state expected Success`() = runTest {

        //Arrange
        val dummyData = listOf(
            TvShowsEntity(
                id = 57243,
                name = "Doctor Who",
                overview = "The Doctor is a Time Lord",
                posterPath = "/4edFyasCrkH4MKs6H4mHqlrxA6b.jpg",
                popularity = 1403.927,
                isFavourite = false
            )
        )
        coEvery { tvShowsRepository.getTVShowsList() } returns flowOf(dummyData)

        //Act
        viewModel.loadData()

        //Assert
        viewModel.weeklyShows.test {
            assertThat(expectMostRecentItem()).isInstanceOf(DataStateWrapper.Idle::class.java)
            awaitEvent()
            val success = expectMostRecentItem()
            assertThat(success).isInstanceOf(DataStateWrapper.Success::class.java)
            assertThat(success.data!!.size).isGreaterThan(0)

            cancel()
        }
    }

    @Test
    fun `Weekly TvShows state expected Fail on downloadTVShowsList() call with Network`() = runTest {

        //Arrange
        coEvery { tvShowsRepository.getTVShowsList() } returns flowOf(emptyList())
        coEvery { tvShowsRepository.downloadTVShowsList() } throws Exception("no internet")

        //Act
        viewModel.loadData()

        //Assert
        viewModel.weeklyShows.test {
            assertThat(expectMostRecentItem()).isInstanceOf(DataStateWrapper.Idle::class.java)
            awaitEvent()//wait for next emit
            val error = expectMostRecentItem()
            assertThat(error).isInstanceOf(DataStateWrapper.Error::class.java)
            assertThat(error.errorMsg).isEqualTo("no internet")

            cancel()
        }
    }

    @Test
    fun `Weekly TvShows state expected Fail on downloadTVShowsList() call with Timeout`() = runTest {

        //Arrange
        coEvery { tvShowsRepository.getTVShowsList() } returns flowOf(emptyList())
        coEvery { tvShowsRepository.downloadTVShowsList() } throws Exception("socket timeout")

        //Act
        viewModel.loadData()

        //Assert
        viewModel.weeklyShows.test {
            assertThat(expectMostRecentItem()).isInstanceOf(DataStateWrapper.Idle::class.java)
            awaitEvent()//wait for next emit
            val error = expectMostRecentItem()
            assertThat(error).isInstanceOf(DataStateWrapper.Error::class.java)
            assertThat(error.errorMsg).isEqualTo("socket timeout")

            cancel()
        }
    }

    @Test
    fun `Weekly TvShows state expected Fail on downloadTVShowsList() call with Unauthorized`() = runTest {

        //Arrange
        coEvery { tvShowsRepository.getTVShowsList() } returns flowOf(emptyList())
        coEvery { tvShowsRepository.downloadTVShowsList() } throws Exception("401 - unauthorized")

        //Act
        viewModel.loadData()

        //Assert
        viewModel.weeklyShows.test {
            assertThat(expectMostRecentItem()).isInstanceOf(DataStateWrapper.Idle::class.java)
            awaitEvent()//wait for next emit
            val error = expectMostRecentItem()
            assertThat(error).isInstanceOf(DataStateWrapper.Error::class.java)
            assertThat(error.errorMsg).isEqualTo("401 - unauthorized")

            cancel()
        }
    }

}