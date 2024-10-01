package uz.gita.uzumcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.uzumcompose.screens.auth.pin.PinScreen
import uz.gita.uzumcompose.screens.auth.repinnn.Example
import uz.gita.uzumcompose.ui.theme.UzumComposeTheme
import uz.gita.uzumcompose.utils.NetworkStatusValidator
import uz.gita.uzumcompose.utils.navigation.NavigationHandler
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler

    @Inject
    lateinit var networkStatusValidator: NetworkStatusValidator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkStatusValidator.listenNetworkStatus(
            onAvailable = {},
            onLost = {}
        )

        setContent {
            UzumComposeTheme {
                Navigator(Example()) { navigator ->
                    LaunchedEffect(key1 = navigator) {
                        navigationHandler.navigationStack
                            .onEach {
                                it.invoke(navigator)
                            }
                            .launchIn(lifecycleScope)
                    }
                    CurrentScreen()
                }
            }
        }
    }
}
