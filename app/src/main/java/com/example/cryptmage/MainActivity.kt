package com.example.cryptmage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.cryptmage.ui.component.floatingAddButton.FloatingAddButton
import com.example.cryptmage.ui.navGraph.AppNavGraph
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.theme.CryptMageTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptMageTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingAddButton({})
                    }
                ) { innerPadding ->
                    AppNavGraph(
                        startDestination = AppRoute.Home,
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                    /*Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptMageTheme {
        Greeting("Android")
    }
}