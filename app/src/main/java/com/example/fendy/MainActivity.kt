package com.example.fendy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fendy.ui.theme.BNITestTheme
import com.example.detail.view.DetailScreen
import com.example.list.view.ListScreen
import com.example.navigation.destination.DetailDestination
import com.example.navigation.destination.ListDestination
import com.example.navigation.navigation.NavigationDestination
import com.example.navigation.navigation.Navigator
import com.example.navigation.navigation.NavigatorEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            BNITestTheme {
                LaunchedEffect(navController) {
                    navigator.destinations.collectLatest {
                        when (val event = it) {
                            is NavigatorEvent.NavigateUp -> navController.navigateUp()
                            is NavigatorEvent.PopBackStack -> navController.popBackStack()
                            is NavigatorEvent.Directions -> navController.navigate(
                                event.destination,
                                event.builder
                            )
                        }
                    }
                }

                val composableDestinations: Map<NavigationDestination, @Composable () -> Unit> =
                    mapOf(
                        ListDestination to { ListScreen() },
                        DetailDestination to { DetailScreen() }
                    )

                NavHost(navController = navController, startDestination = ListDestination.route()) {
                    composableDestinations.forEach { entry ->
                        val destination = entry.key
                        composable(destination.route(), destination.arguments) {
                            entry.value()
                        }
                    }
                }
            }
        }
    }
}
