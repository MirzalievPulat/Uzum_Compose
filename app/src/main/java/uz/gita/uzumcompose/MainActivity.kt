package uz.gita.uzumcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.common.other.NetworkStatusValidator
import uz.gita.uzumcompose.screens.auth.splash.SplashScreen
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.utils.navigation.NavigationHandler
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler

    @Inject
    lateinit var networkStatusValidator: NetworkStatusValidator


    @OptIn(ExperimentalVoyagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkStatusValidator.listenNetworkStatus(
            onAvailable = {},
            onLost = {}
        )


        setContent {
            UzumComposeTheme {
                Navigator(screen = SplashScreen(),
                        disposeBehavior = NavigatorDisposeBehavior(disposeSteps = false)
                ) { navigator ->
                    LaunchedEffect(key1 = navigator) {
                        navigationHandler.navigationStack
                            .onEach {
                                it.invoke(navigator)
                            }
                            .launchIn(lifecycleScope)
                    }
                    SlideTransition(
                        navigator = navigator,
                        disposeScreenAfterTransitionEnd = true
                    )
                }
            }
        }
    }
}
