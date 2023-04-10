package com.example.shared.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.shared.data.databases.LocalDatabase
import com.example.shared.data.databases.model.UserModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class DataBaseTest {

    private lateinit var db: LocalDatabase

    @Before
    fun setup() {
        db = Room
            .inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                LocalDatabase::class.java
            )
            .build()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertDatabaseLocal() = runTest {

        val listData = UserModel(1, "super,1.0", 1.0, "sasd", "asd", true)

        db.daoDatabase().insert(userModel = listData)
        val result = db.daoDatabase().getAllUserById(1)

        assertEquals(result, listData)
    }

    @Test
    fun updateDatabaseLocal() = runTest {

        val listData = UserModel(1, "super,1.0", 1.0, "sasd", "asd", true)
        val listDataUpdate = UserModel(1, "super", 2.0, "bb", "asd", false)

        db.daoDatabase().insert(userModel = listData)
        db.daoDatabase().update(userModel = listDataUpdate)
        val result = db.daoDatabase().getAllUserById(1)

        assertEquals(result, listDataUpdate)
    }

    @Test
    fun deleteDatabaseLocal() = runTest {

        val listData = UserModel(1, "super,1.0", 1.0, "sasd", "asd", true)

        db.daoDatabase().insert(userModel = listData)
        db.daoDatabase().delete(userModel = listData)
        val result = db.daoDatabase().getAllUserById(1)

        assertEquals(result, null)
    }

    @Test
    fun getDatabaseLocal() = runTest {

        val listData = UserModel(1, "super,1.0", 1.0, "sasd", "asd", true)

        db.daoDatabase().insert(userModel = listData)
        val result = db.daoDatabase().getAllUsers()

        assertEquals(result, listOf(listData))
    }

}
