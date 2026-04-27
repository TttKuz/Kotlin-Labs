package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun App() {

    val products = listOf(
        "Молоко (2)",
        "Яйца (10)",
        "Хлеб (1)",
        "Сыр (1)",
        "Яблоки (5)",
        "Картофель (3 кг)"
    )

    LazyColumn {
        items(products) {
            ShoppingListElement(it)
        }
    }
}

@Composable
fun ShoppingListElement(text: String) {
    Text(text)
}