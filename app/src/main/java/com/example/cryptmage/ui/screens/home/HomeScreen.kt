package com.example.cryptmage.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptmage.ui.navGraph.AppNavController
import com.example.cryptmage.ui.navGraph.AppRoute
import com.example.cryptmage.ui.screens.home.items.VaultCard
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsState()
    val vaultEntries = viewState.value.vaultEntries.collectAsState(emptyList())
    val navController = AppNavController.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.sdp),
            verticalArrangement = Arrangement.spacedBy(12.sdp),
            overscrollEffect = null
        ) {
            items(
                items = vaultEntries.value,
                key = { it.id!! }
            ) { enter ->
                VaultCard(enter, onClick = { 
                    enter.id?.let { id ->
                        navController.navigate(AppRoute.Details(id))
                    }
                })
            }
        }
    }

    // TODO: Add FAB in MainActivity or handle differently
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}