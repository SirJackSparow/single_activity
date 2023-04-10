package com.example.detail

import androidx.lifecycle.SavedStateHandle
import com.example.detail.DetailMockData.detailData
import com.example.detail.vm.DetailUiState
import com.example.detail.vm.DetailViewModel
import com.example.navigation.destination.DetailDestination
import com.example.navigation.navigation.Navigator
import com.example.shared.domain.GetDetailUserUseCase
import com.example.shared.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var vm: DetailViewModel
    @MockK
    private lateinit var navigator: Navigator

    @MockK
    private lateinit var savedStateHandle: SavedStateHandle

    @MockK
    private lateinit var getUserDetailUseCase: GetDetailUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        vm = DetailViewModel(
            navigator = navigator,
            savedStateHandle = savedStateHandle,
            useCase = getUserDetailUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun successCallGetDetailUsers() {
        assertEquals(DetailUiState.Loading, vm.uiState.value)
        coEvery { savedStateHandle.get<String>(DetailDestination.ID_PARAM) } returns "super"
        coEvery { getUserDetailUseCase(any()) } returns Resource.Success(detailData)

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(DetailUiState.Shows(detailData), vm.uiState.value)
    }

    @Test
    fun failedCallGetUserDetail() {
        assertEquals(DetailUiState.Loading, vm.uiState.value)
        coEvery { savedStateHandle.get<String>(DetailDestination.ID_PARAM) } returns "super"
        coEvery { getUserDetailUseCase(any()) } returns Resource.Failed("Failed")

        dispatcher.scheduler.advanceUntilIdle()

        assertEquals(DetailUiState.Error("Failed"), vm.uiState.value)
    }


}
