package com.example.shared.data.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shared.data.databases.model.UserModel
import javax.inject.Singleton

@Database(entities = [UserModel::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase(){
    abstract fun daoDatabase() : UserDao
}