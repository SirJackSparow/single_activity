package com.example.shared.domain

import com.example.shared.data.network.model.UsersRandomModel
import com.example.shared.data.repositories.GetUsersRepo
import com.example.shared.utils.Resource
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val getUsersRepo: GetUsersRepo
)  {
    suspend operator fun invoke(q: String, page: Int,sort: String) : Resource<UsersRandomModel> =
        getUsersRepo.getUsersList(q = q, page = page, sort = sort)
}