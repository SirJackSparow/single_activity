package com.example.shared.data.repositories

import android.util.Log
import com.example.shared.data.network.GithubApi
import com.example.shared.data.network.model.UsersRandomModel
import com.example.shared.utils.Resource
import javax.inject.Inject

class GetUsersRepoImpl @Inject constructor(private val service: GithubApi) : GetUsersRepo {
    override suspend fun getUsersList(
        page: Int,
        q: String,
        sort: String
    ): Resource<UsersRandomModel> {
        return try {
            val response = service.getUsers(q = q, page = page, sort = sort)
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
}
