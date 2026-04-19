package com.example.cryptmage.ui.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cryptmage.data.enums.BottomNavDestination
import com.example.cryptmage.data.repository.SessionManager
import com.example.cryptmage.ui.component.appBottomNavBar.AppBottomNavBar
import com.example.cryptmage.ui.component.appTopBar.AppTopBar
import com.example.cryptmage.ui.component.floatingAddButton.FloatingAddButton
import com.example.cryptmage.ui.component.snackbar.AppSnackBar
import com.example.cryptmage.ui.component.snackbar.SnackBarController
import com.example.cryptmage.ui.navGraph.AppNavController
import com.example.cryptmage.ui.navGraph.AppNavGraph
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.theme.CryptMageTheme
import com.example.cryptmage.utils.HelperMethods
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )*/

        enableEdgeToEdge()
        setContent {
            CryptMageTheme {
                val navController = rememberNavController()
                val sessionManager: SessionManager = koinInject()
                val databaseSession by sessionManager.databaseFlow.collectAsStateWithLifecycle()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination by remember(navBackStackEntry) {
                    derivedStateOf { navBackStackEntry?.destination }
                }
                val selectedDestination = BottomNavDestination.entries.firstOrNull {
                    it.route::class.qualifiedName == navBackStackEntry?.destination?.route
                }

                val snackBarController: SnackBarController = koinInject()
                val snackBarState by snackBarController.snackBarState.collectAsStateWithLifecycle()

                LaunchedEffect(databaseSession) {
                    if (databaseSession == null) {
                        navController.navigate(AppRoute.Login) {
                            popUpTo(0)
                        }
                    }
                }
                HelperMethods.createLog("Current Route: ${navBackStackEntry?.destination?.route}")
                HelperMethods.createLog("Bottom Nav Route: ${BottomNavDestination.Home.route::class.qualifiedName}")
                CompositionLocalProvider(AppNavController provides navController) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            key(navBackStackEntry?.destination?.route) {
                                when (currentDestination?.route) {
                                    AppRoute.Login::class.qualifiedName -> { }
                                    else -> AppTopBar(
                                        destination = currentDestination,
                                        onNavigateUp = { navController.navigateUp() }
                                    )
                                }
                            }
                        },
                        bottomBar = {
                            if (selectedDestination != null) {
                                AppBottomNavBar(
                                    selected = selectedDestination,
                                    onItemClick = { destination ->
                                        navController.navigate(destination.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        },
                        snackbarHost = {
                            AppSnackBar(state = snackBarState)
                        },
                    ) { innerPadding ->
                        AppNavGraph(
                            modifier = Modifier.padding(innerPadding),
                            startDestination = AppRoute.Login,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}