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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cryptmage.data.repository.SessionManager
import com.example.cryptmage.ui.component.appTopBar.AppTopBar
import com.example.cryptmage.ui.component.floatingAddButton.FloatingAddButton
import com.example.cryptmage.ui.component.snackbar.AppSnackBar
import com.example.cryptmage.ui.component.snackbar.SnackBarController
import com.example.cryptmage.ui.navGraph.AppNavController
import com.example.cryptmage.ui.navGraph.AppNavGraph
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.theme.CryptMageTheme
import org.koin.compose.koinInject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        enableEdgeToEdge()
        setContent {
            CryptMageTheme {
                val navController = rememberNavController()
                val sessionManager: SessionManager = koinInject()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination by remember(navBackStackEntry) {
                    derivedStateOf { navBackStackEntry?.destination }
                }

                val snackBarController: SnackBarController = koinInject()
                val snackBarState by snackBarController.snackBarState.collectAsStateWithLifecycle()

                LaunchedEffect(sessionManager.database) {
                    if (sessionManager.database == null) {
                        navController.navigate(AppRoute.Login) {
                            popUpTo(0)
                        }
                    }
                }

                CompositionLocalProvider(AppNavController provides navController) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            when (currentDestination?.route) {
                                AppRoute.Login::class.qualifiedName -> { }
                                else -> AppTopBar(
                                    destination = currentDestination,
                                    onNavigateUp = { navController.navigateUp() }
                                )
                            }
                        },
                        snackbarHost = {
                            AppSnackBar(state = snackBarState)
                        },
                        floatingActionButton = {
                            if (currentDestination?.route == AppRoute.Home::class.qualifiedName) {
                                FloatingAddButton {
                                    navController.navigate(AppRoute.GeneratePassword)
                                }
                            }
                        }
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