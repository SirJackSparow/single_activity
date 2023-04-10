package com.example.shared.data.network

import com.example.shared.data.network.model.UsersRandomModel
import com.example.shared.data.network.model.UsersRandomModelItem

object ListMock {

    val listUsers = UsersRandomModel(
        total_count = 10,
        incomplete_results = false,
        items = listOf(
            UsersRandomModelItem(
                login = "super",
                id = 44467,
                node_id = "MDQ6VXNlcjQ0NDY3",
                avatar_url = "https://avatars.githubusercontent",
                gravatar_id = "",
                url = "https://api.github.com/users/super",
                html_url = "https://github.com/super",
                followers_url = "https://api.github.com/users/super/followers",
                following_url = "https://api.github.com/users/super/following{/other_user}",
                gists_url = "https://api.github.com/users/super/gists{/gist_id}",
                starred_url = "https://api.github.com/users/super/starred{/owner}{/repo}",
                subscriptions_url = "https://api.github.com/users/super/subscriptions",
                organizations_url = "https://api.github.com/users/super/orgs",
                repos_url = "https://api.github.com/users/super/repos",
                events_url = "https://api.github.com/users/super/events{/privacy}",
                received_events_url = "https://api.github.com/users/super/received_events",
                type = "User",
                site_admin = false,
                score = 1.0
            )
        )
    )
}