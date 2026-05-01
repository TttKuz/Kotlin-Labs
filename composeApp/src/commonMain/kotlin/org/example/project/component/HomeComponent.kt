package org.example.project.component

import com.arkivanov.decompose.ComponentContext

interface HomeComponent {
    fun navigateToSecondScreen(param: String)
}

class HomeComponentImpl(
    private val onNavigateToSecondScreen: (String) -> Unit,
    componentContext: ComponentContext,
) : HomeComponent, ComponentContext by componentContext {
    override fun navigateToSecondScreen(param: String) {
        onNavigateToSecondScreen(param)
    }
}
