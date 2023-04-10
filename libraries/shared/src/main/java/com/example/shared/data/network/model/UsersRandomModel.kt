package com.example.shared.data.network.model

data class UsersRandomModel(
    val incomplete_results: Boolean,
    val items: List<UsersRandomModelItem>,
    val total_count: Int
)