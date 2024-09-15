package uz.gita.uzumcompose.presentation.auth.signIn

import uz.gita.uzumcompose.presentation.auth.signInVerify.SignInVerifyScreen
import uz.gita.uzumcompose.presentation.auth.signUp.SignUpScreen
import uz.gita.uzumcompose.utils.navigation.AppNavigator
import javax.inject.Inject

class SignInDirections @Inject constructor(private val appNavigator: AppNavigator) : SignInContract.Direction {
    override suspend fun moveToSignUp() {
        appNavigator.replace(SignUpScreen())
    }

    override suspend fun moveToVerify(phoneNumber: String) {
        appNavigator.navigateTo(SignInVerifyScreen(phoneNumber))
    }

}