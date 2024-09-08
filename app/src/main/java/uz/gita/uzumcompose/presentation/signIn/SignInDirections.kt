package uz.gita.uzumcompose.presentation.signIn

import uz.gita.uzumcompose.presentation.signUpVerify.SignUpVerifyScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SignInDirections @Inject constructor(private val appNavigator: AppNavigator):SignInContract.Direction {
    override suspend fun moveToSignUp() {
        appNavigator.back()
    }

    override suspend fun moveToVerify() {
        appNavigator.navigateTo(SignUpVerifyScreen())
    }

}