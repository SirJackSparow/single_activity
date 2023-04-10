package com.example.list

import com.example.shared.data.network.model.UsersRandomModel
import com.example.shared.data.network.model.UsersRandomModelItem

object ListMock {

    val listUsers = UsersRandomModel(
        incomplete_results = false,
        items = listOf(
            UsersRandomModelItem(
                "asd",
                "dasd",
                "asdas",
                "da",
                "dasd",
                "das",
                "asdasd",
                1,
                "sdasd",
                "sdasd",
                "asd",
                "asdasd",
                "sdasd",
                1.0,
                true,
                "aasdasd",
                "sdasda",
                "asdasd",
                "sdas"
            )
        ),
        total_count = 10
    )
}