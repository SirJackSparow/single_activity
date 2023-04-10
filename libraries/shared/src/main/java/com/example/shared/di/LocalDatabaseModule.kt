package com.example.shared.di

import android.content.Context
import androidx.room.Room
import com.example.shared.data.databases.LocalDatabase
import com.example.shared.data.databases.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @Provides
    @Singleton
    fun localDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, "Users")
            .build()
    }

    @Provides
    fun provideDao(myDatabase: LocalDatabase): UserDao {
        return myDatabase.daoDatabase()
    }

}
