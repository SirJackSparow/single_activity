package com.example.shared.data.repositories

import com.example.shared.data.databases.model.UserModel
import com.example.shared.data.network.model.DetailModel
import com.example.shared.utils.Resource

interface GetDetailUserRepo {

    suspend fun getDetailUser(username: String): Resource<DetailModel>

    suspend fun addData(user: UserModel)

    suspend fun deleteData(user: UserModel)

    suspend fun getUserById(id: Int): Resource<UserModel>
}
