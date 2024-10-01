package uz.gita.uzumcompose.screens.auth.signUp

import uz.gita.presentation.auth.signUp.SignUpContract
import uz.gita.uzumcompose.screens.auth.signIn.SignInScreen
import uz.gita.uzumcompose.screens.auth.signUpVerify.SignUpVerifyScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SignUpDirections @Inject constructor(private val appNavigator: AppNavigator) : SignUpContract.Direction {
    override suspend fun moveToSignIn() {
        appNavigator.replace(SignInScreen())
    }

    override suspend fun moveToVerify(phoneNumber: String) {
        appNavigator.navigateTo(SignUpVerifyScreen(phoneNumber))
    }
}