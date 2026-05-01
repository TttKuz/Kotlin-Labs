package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.compose_multiplatform
import kotlinproject.composeapp.generated.resources.decrement
import kotlinproject.composeapp.generated.resources.increment
import kotlinproject.composeapp.generated.resources.parametrised
import kotlinproject.composeapp.generated.resources.parametrised2
import kotlinproject.composeapp.generated.resources.string1
import kotlinproject.composeapp.generated.resources.string2
import kotlinproject.composeapp.generated.resources.things
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun App() {
    Column {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null
        )

        Text(stringResource(Res.string.string1))
        Text(stringResource(Res.string.string2))
        Text(stringResource(Res.string.parametrised, 100))

        var input by remember { mutableStateOf("") }
        TextField(value = input, onValueChange = { input = it })
        Text(stringResource(Res.string.parametrised2, input))

        Spacer(modifier = Modifier.height(16.dp))

        var count by remember { mutableIntStateOf(1) }

        Button(onClick = { count += 1 }) {
            Text(stringResource(Res.string.increment))
        }
        Button(onClick = { if (count > 0) count -= 1 }) {
            Text(stringResource(Res.string.decrement))
        }

        Text(pluralStringResource(Res.plurals.things, count, count))
    }
}