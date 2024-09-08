package uz.gita.uzumcompose.presentation.signUp

import uz.gita.uzumcompose.presentation.signIn.SignInScreen
import uz.gita.uzumcompose.presentation.signUpVerify.SignUpVerifyScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SignUpDirections @Inject constructor(private val appNavigator: AppNavigator):SignUpContract.Direction {
    override suspend fun moveToSignIn() {
        appNavigator.navigateTo(SignInScreen())
    }

    override suspend fun moveToVerify() {
        appNavigator.navigateTo(SignUpVerifyScreen())
    }
}