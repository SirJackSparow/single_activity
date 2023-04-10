package com.example.shared.di

import com.example.shared.data.repositories.GetDetailUserRepo
import com.example.shared.data.repositories.GetDetailUserRepoImpl
import com.example.shared.data.repositories.GetUsersRepo
import com.example.shared.data.repositories.GetUsersRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModules {

    @Binds
    @Singleton
    abstract fun getUsers(getUsersRepoImpl: GetUsersRepoImpl) : GetUsersRepo

    @Binds
    @Singleton
    abstract fun getDetail(getDetailImpl:GetDetailUserRepoImpl) : GetDetailUserRepo
}