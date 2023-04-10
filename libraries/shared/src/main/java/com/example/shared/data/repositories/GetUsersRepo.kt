package com.example.shared.data.repositories

import com.example.shared.data.network.model.UsersRandomModel
import com.example.shared.utils.Resource

interface GetUsersRepo {

    suspend fun getUsersList(page: Int, q: String, sort: String): Resource<UsersRandomModel>
}