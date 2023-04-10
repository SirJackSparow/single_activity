package com.example.shared.data.databases

import androidx.room.*
import com.example.shared.data.databases.model.UserModel

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userModel: UserModel)

    @Delete
    suspend fun delete(userModel: UserModel)

    @Update
    suspend fun update(userModel: UserModel)

    @Query("select * from UserModel")
    suspend fun getAllUsers(): List<UserModel>

    @Query("select * from UserModel where id = :id ")
    suspend fun getAllUserById(id: Int): UserModel
}