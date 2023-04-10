package com.example.list

import com.example.list.ListMock.listUsers
import com.example.list.vm.ListUiState
import com.example.list.vm.ListViewModel
import com.example.navigation.navigation.Navigator
import com.example.shared.data.network.model.UsersRandomModel
import com.example.shared.domain.GetUsersUseCase
import com.example.shared.utils.Resource
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var vm: ListViewModel

    @MockK
    private lateinit var navigator: Navigator

    @MockK
    private lateinit var getUsersUseCase: GetUsersUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(dispatcher)
        vm = ListViewModel(navigator, getUsersUseCase)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }


    @Test
    fun successfulGetUsers() = runTest {
        assertEquals(ListUiState.Loading, vm.uiState.value)

        coEvery { getUsersUseCase(any(), any(), any()) } returns Resource.Success(listUsers)

        dispatcher.scheduler.advanceUntilIdle()

        coVerify { getUsersUseCase(any(),any(),any()) }
        assertEquals(ListUiState.Shows(listUsers.items,false),vm.uiState.value)
    }

    @Test
    fun failedGetUser() = runTest {
        assertEquals(ListUiState.Loading, vm.uiState.value)

        coEvery { getUsersUseCase(any(), any(), any()) } returns Resource.Failed("Failed")

        dispatcher.scheduler.advanceUntilIdle()

        coVerify { getUsersUseCase(any(),any(),any()) }
        assertEquals(ListUiState.Error("Failed"),vm.uiState.value)

    }

    @Test
    fun dataNotFoundGetUserWhenDataIsEmpty() = runTest {
        assertEquals(ListUiState.Loading, vm.uiState.value)
        val emptyListDummy = UsersRandomModel(true, emptyList(),0)
        coEvery { getUsersUseCase(any(), any(), any()) } returns Resource.Success(emptyListDummy)

        dispatcher.scheduler.advanceUntilIdle()

        coVerify { getUsersUseCase(any(),any(),any()) }

        //message will be data not found
        assertEquals(ListUiState.Error("Data not found"),vm.uiState.value)

    }

    @Test
    fun exceptionHandlerError() = runTest {
        assertEquals(ListUiState.Loading, vm.uiState.value)
        coEvery { getUsersUseCase(any(),any(), any()) } throws Exception("")

        dispatcher.scheduler.advanceUntilIdle()

        coVerify { getUsersUseCase(any(),any(),any()) }

        assertEquals(ListUiState.Error(""), vm.uiState.value)
    }

}