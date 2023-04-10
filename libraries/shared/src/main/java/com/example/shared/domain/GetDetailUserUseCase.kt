package com.example.shared.domain

import com.example.shared.data.databases.model.UserModel
import com.example.shared.data.network.model.DetailModel
import com.example.shared.data.repositories.GetDetailUserRepo
import com.example.shared.utils.Resource
import javax.inject.Inject

class GetDetailUserUseCase @Inject constructor(private val repo: GetDetailUserRepo) {

    suspend operator fun invoke(userName: String): Resource<DetailModel> =
        repo.getDetailUser(userName)

    suspend fun addLocal(user: UserModel) {
        repo.addData(user)
    }

    suspend fun deleteLocal(user: UserModel) {
        repo.deleteData(user)
    }

    suspend fun getUserById(id: Int): Resource<UserModel> {
        return repo.getUserById(id)
    }
}
