package org.example.project

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.adaptive.navigationsuite.rememberNavigationSuiteScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.about_screen_text
import kotlinproject.composeapp.generated.resources.about_screen_topbar_title
import kotlinproject.composeapp.generated.resources.home_screen_go_to_second_screen_button
import kotlinproject.composeapp.generated.resources.home_screen_go_to_second_screen_text_field_label
import kotlinproject.composeapp.generated.resources.home_screen_topbar_title
import kotlinproject.composeapp.generated.resources.second_screen_topbar_title
import org.example.project.component.AboutComponent
import org.example.project.component.HomeComponent
import org.example.project.component.RootComponent
import org.example.project.component.SecondComponent
import org.jetbrains.compose.resources.stringResource
import ui.theme.getApplicationColorScheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun App(rootComponent: RootComponent) {
    MaterialTheme(colorScheme = getApplicationColorScheme()) {
        val navigationSuiteType = with(currentWindowAdaptiveInfo()) {
            when {
                windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) ->
                    NavigationSuiteType.WideNavigationRailExpanded

                windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND) ->
                    NavigationSuiteType.WideNavigationRailCollapsed

                else -> NavigationSuiteType.ShortNavigationBarCompact
            }
        }

        val navigationScaffoldState = rememberNavigationSuiteScaffoldState()
        val childStack by rootComponent.childStack.subscribeAsState()

        LaunchedEffect(childStack.active.configuration) {
            if(childStack.active.configuration is RootComponent.Config.MainScreen) {
                navigationScaffoldState.show()
            } else {
                navigationScaffoldState.hide()
            }
        }

        NavigationSuiteScaffold(
            navigationItems = {
                NavigationSuiteItem(
                    selected = childStack.active.configuration == RootComponent.Config.Home,
                    onClick = { rootComponent.navigate(RootComponent.Config.Home) },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text(stringResource(Res.string.home_screen_topbar_title)) },
                    navigationSuiteType = navigationSuiteType
                )
                NavigationSuiteItem(
                    selected = childStack.active.configuration == RootComponent.Config.About,
                    onClick = { rootComponent.navigate(RootComponent.Config.About) },
                    icon = { Icon(Icons.Default.Info, contentDescription = null) },
                    label = { Text(stringResource(Res.string.about_screen_topbar_title)) },
                    navigationSuiteType = navigationSuiteType
                )
            },
            navigationSuiteType = navigationSuiteType,
            state = navigationScaffoldState
        ) {
            Children(rootComponent.childStack, animation = stackAnimation(fade())) {
                when (val child = it.instance) {
                    is RootComponent.Child.Home -> HomeScreenContent(child.component)
                    is RootComponent.Child.Second -> SecondScreenContent(child.component)
                    is RootComponent.Child.About -> AboutScreenContent(child.component)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun HomeScreenContent(component: HomeComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.home_screen_topbar_title))
                },
            )
        },
    ) { contentPadding ->
        Column(
            Modifier.padding(contentPadding)
        ) {
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text(stringResource(Res.string.home_screen_go_to_second_screen_text_field_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    component.navigateToSecondScreen(text)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(Res.string.home_screen_go_to_second_screen_button))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SecondScreenContent(component: SecondComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.second_screen_topbar_title))
                },
            )
        },
    ) { contentPadding ->
        Column(
            Modifier.padding(contentPadding)
        ) {
            SelectionContainer {
                Text(component.param)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AboutScreenContent(component: AboutComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(Res.string.about_screen_topbar_title))
                },
            )
        },
    ) { contentPadding ->
        Column(
            Modifier.padding(contentPadding)
        ) {
            Text(stringResource(Res.string.about_screen_text))
        }
    }
}
