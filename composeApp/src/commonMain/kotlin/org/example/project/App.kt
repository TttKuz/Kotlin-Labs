package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import org.example.project.ui.util.adaptiveHorizontalPadding
import kotlinx.coroutines.launch
import ui.theme.getApplicationColorScheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun App() {
    MaterialTheme(colorScheme = getApplicationColorScheme()) {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        var counter by remember { mutableIntStateOf(0) }
        var showDialog by remember { mutableStateOf(false) }

        // Получаем класс окна для адаптивности
        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val isWide = windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Лабораторная №5") },
                    actions = {
                        IconButton(onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Снекбар! Действие необратимо? Нет, просто пример.")
                            }
                        }) {
                            Icon(Icons.Default.Add, contentDescription = "Показать снекбар")
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { contentPadding ->
            // Адаптивная разметка: Row на широких экранах, Column на узких
            if (isWide) {
                Row(
                    modifier = Modifier
                        .padding(contentPadding)
                        .adaptiveHorizontalPadding(windowSizeClass)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CounterCard(counter, onIncrement = { counter++ }, onResetClick = { showDialog = true })
                    SampleListCard()
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .adaptiveHorizontalPadding(windowSizeClass)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CounterCard(counter, onIncrement = { counter++ }, onResetClick = { showDialog = true })
                    SampleListCard()
                }
            }
        }

        // Диалог подтверждения (необратимое действие — сброс счётчика)
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Сбросить счётчик?") },
                text = { Text("Значение будет потеряно. Вы уверены?") },
                icon = { Icon(Icons.Default.Warning, contentDescription = null) },
                confirmButton = {
                    TextButton(
                        onClick = {
                            counter = 0
                            showDialog = false
                        }
                    ) { Text("Сбросить") }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) { Text("Отмена") }
                }
            )
        }
    }
}

@Composable
fun CounterCard(counter: Int, onIncrement: () -> Unit, onResetClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Счётчик: $counter", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onIncrement) { Text("Увеличить") }
                OutlinedButton(onClick = onResetClick) { Text("Сбросить") }
            }
        }
    }
}

@Composable
fun SampleListCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(20) { index ->
                Text("Элемент списка #${index + 1}")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}