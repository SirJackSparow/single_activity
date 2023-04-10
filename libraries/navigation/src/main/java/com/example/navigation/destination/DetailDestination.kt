package com.example.navigation.destination

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.navigation.navigation.NavigationDestination

object DetailDestination : NavigationDestination {

    const val ID_PARAM = "userName"
    private const val DETAIL_ROUTE_PARAM = "detailRoute"
    private const val DETAIL_ROUTE = "$DETAIL_ROUTE_PARAM/{$ID_PARAM}"

    override fun route(): String = DETAIL_ROUTE

    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(ID_PARAM) { type = NavType.StringType })

    fun createDetailsRoute(userName: String) = "$DETAIL_ROUTE_PARAM/$userName"
}