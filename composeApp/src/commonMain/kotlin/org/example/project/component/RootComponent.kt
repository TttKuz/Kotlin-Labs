package org.example.project.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.backhandler.BackHandler
import kotlinx.serialization.Serializable

interface RootComponent {
    val childStack: Value<ChildStack<Config, RootComponent.Child>>

    fun navigate(config: Config.MainScreen)

    sealed interface Child {
        class Home(val component: HomeComponent) : Child
        class Second(val component: SecondComponent) : Child
        class About(val component: AboutComponent) : Child
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Home : Config, MainScreen

        @Serializable
        data class Second(val param: String) : Config

        @Serializable
        data object About : Config, MainScreen

        sealed interface MainScreen: Config
    }
}

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<RootComponent.Config>()

    override val childStack: Value<ChildStack<RootComponent.Config, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = RootComponent.Config.serializer(),
        initialConfiguration = RootComponent.Config.Home,
        handleBackButton = true,
        childFactory = { cfg, ctx ->
            when (cfg) {
                is RootComponent.Config.Second -> RootComponent.Child.Second(SecondComponentImpl(
                    param = cfg.param,
                    componentContext = ctx
                ))
                is RootComponent.Config.Home -> RootComponent.Child.Home(HomeComponentImpl(
                    onNavigateToSecondScreen = {
                        navigation.pushNew(RootComponent.Config.Second(it))
                    },
                    componentContext = ctx
                ))
                is RootComponent.Config.About -> RootComponent.Child.About(AboutComponentImpl(ctx))
            }
        },
    )

    override fun navigate(config: RootComponent.Config.MainScreen) {
        navigation.bringToFront(config)
    }
}
