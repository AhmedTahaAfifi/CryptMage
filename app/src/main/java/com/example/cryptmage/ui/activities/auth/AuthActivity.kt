package com.example.cryptmage.ui.activities.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.cryptmage.ui.component.snackbar.AppSnackBar
import com.example.cryptmage.ui.component.snackbar.SnackBarController
import com.example.cryptmage.ui.navGraph.AppNavController
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.navGraph.AuthNavGraph.AuthNavGraph
import com.example.cryptmage.ui.theme.CryptMageTheme
import org.koin.compose.koinInject

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptMageTheme {
                val navController = rememberNavController()
                val snackBarController: SnackBarController = koinInject()
                val snackBarState by snackBarController.snackBarState.collectAsStateWithLifecycle()

                CompositionLocalProvider(AppNavController provides navController) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = {
                            AppSnackBar(state = snackBarState)
                        },
                    ) { innerPadding ->
                        AuthNavGraph(
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