package com.example.shared.data.repositories

import com.example.shared.data.databases.UserDao
import com.example.shared.data.databases.model.UserModel
import com.example.shared.data.network.GithubApi
import com.example.shared.data.network.model.DetailModel
import com.example.shared.utils.Resource
import javax.inject.Inject

class GetDetailUserRepoImpl @Inject constructor(
    private val service: GithubApi,
    private val dao: UserDao
) : GetDetailUserRepo {
    override suspend fun getDetailUser(username: String): Resource<DetailModel> {
        return try {
            val response = service.detailUser(username)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Failed(message = response.message())
            }
        } catch (e: Exception) {
            Resource.Failed(message = e.message ?: "Error Message")
        }
    }

    override suspend fun addData(user: UserModel) {
        dao.insert(user)
    }

    override suspend fun deleteData(user: UserModel) {
        dao.delete(user)
    }

    override suspend fun getUserById(id: Int): Resource<UserModel> {
        val data = dao.getAllUserById(id)
        return if (data != null) {
            Resource.Success(dao.getAllUserById(id))
        } else {
            Resource.Failed("Data is Empty")
        }
    }
}
