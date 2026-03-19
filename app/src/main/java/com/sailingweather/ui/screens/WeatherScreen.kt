package com.sailingweather.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sailing
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sailingweather.model.WeatherConditions
import com.sailingweather.ui.components.ConditionsCard
import com.sailingweather.ui.components.ForecastCard
import com.sailingweather.ui.components.SunMoonCard
import com.sailingweather.ui.components.WindCard
import com.sailingweather.ui.theme.GoodSailing
import com.sailingweather.viewmodel.WeatherUiState
import com.sailingweather.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Sailing,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Sailing Weather", fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { viewModel.refresh() },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState) {
                is WeatherUiState.Loading -> LoadingContent()
                is WeatherUiState.Success -> WeatherContent(state.conditions)
                is WeatherUiState.Error -> ErrorContent(state.message) { viewModel.refresh() }
            }
        }
    }
}

@Composable
private fun WeatherContent(conditions: WeatherConditions) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Sailing status badge
        if (conditions.isGoodSailing) {
            Surface(
                color = GoodSailing.copy(alpha = 0.15f),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    "Good Sailing Conditions",
                    style = MaterialTheme.typography.labelLarge,
                    color = GoodSailing,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }

        // Last updated
        Text(
            "Updated: ${conditions.timestamp}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        WindCard(conditions)
        ConditionsCard(conditions)
        SunMoonCard(conditions)
        ForecastCard(conditions)

        Spacer(Modifier.height(16.dp))
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(16.dp))
            Text(
                "Fetching conditions...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(
                "Unable to fetch weather data",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            Text(
                message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}
