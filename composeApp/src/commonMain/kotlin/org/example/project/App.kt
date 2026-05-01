package org.example.project

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.saveable.rememberSaveable
import ui.theme.getApplicationColorScheme

@Composable
fun App() {
    MaterialTheme(colorScheme = getApplicationColorScheme()) {
        Scaffold { contentPadding ->
            var state by rememberSaveable { mutableStateOf(false) }
            Column(Modifier.padding(contentPadding)) {
                Button(onClick = { state = !state }) {
                    Text("Переключить")
                }
                Text("Состояние: $state")
            }
        }
    }
}