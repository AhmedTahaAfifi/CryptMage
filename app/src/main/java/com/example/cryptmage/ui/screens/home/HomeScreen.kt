package com.example.cryptmage.ui.screens.home

import androidx.compose.foundation.layout.Column
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
import com.example.cryptmage.ui.screens.home.items.VaultCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val viewState = viewModel.viewState.collectAsState()
    val vaultEntries = viewState.value.vaultData.collectAsState(emptyList())
    val navController = AppNavController.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 20.dp),
            overscrollEffect = null
        ) {
            items(vaultEntries.value) {
                VaultCard(it)
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