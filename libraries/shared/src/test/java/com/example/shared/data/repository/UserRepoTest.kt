package com.example.shared.data.repository

import com.example.shared.data.databases.UserDao
import com.example.shared.data.network.DetailMockData.detailData
import com.example.shared.data.network.GithubApi
import com.example.shared.data.network.ListMock
import com.example.shared.data.repositories.GetDetailUserRepo
import com.example.shared.data.repositories.GetDetailUserRepoImpl
import com.example.shared.data.repositories.GetUsersRepo
import com.example.shared.data.repositories.GetUsersRepoImpl
import com.example.shared.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepoTest {

    private lateinit var userRepo: GetUsersRepo
    private lateinit var userDetailRepo: GetDetailUserRepo

    @MockK
    private lateinit var api: GithubApi

    @MockK
    private lateinit var dao: UserDao

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        userRepo = GetUsersRepoImpl(
            api
        )
        userDetailRepo = GetDetailUserRepoImpl(
            api, dao
        )
    }

    @Test
    fun getData() = runBlocking {
        coEvery { api.getUsers(any(), any(), any(), any()).body() } returns ListMock.listUsers

        userRepo.getUsersList(1, "super", "")

        coEvery { api.getUsers(any(), any(), any(), any()).body() }

        assertEquals(ListMock.listUsers.items, Resource.Success(ListMock.listUsers.items).data)
    }

    @Test
    fun getDetailData() = runBlocking {
        coEvery { api.detailUser(any()).body() } returns detailData

        userDetailRepo.getDetailUser("super")

        coEvery { api.detailUser(any()).body() }

        assertEquals(detailData, Resource.Success(detailData).data)
    }
}