package com.example.navigation.destination

import com.example.navigation.navigation.NavigationDestination


object ListDestination : NavigationDestination{
        private const val LIST_ROUTE = "listRoute"
        override fun route(): String = LIST_ROUTE
}