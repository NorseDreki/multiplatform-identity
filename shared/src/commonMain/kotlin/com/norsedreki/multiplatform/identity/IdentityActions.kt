package com.norsedreki.multiplatform.identity

sealed interface IdentityActions : Event {

    sealed interface WithPassword : IdentityActions {

    }

    sealed interface PasswordLess : IdentityActions {
        object Passkey: PasswordLess //Google or Apple passkey support
        // for sites supporting sign in with Google / sign in with Apple


    }

    object AnonymousLogin : IdentityActions

    sealed interface LogIn : IdentityActions {
        data class WithPassword(val email: String, val password: String) : LogIn

        object WithGoogle : LogIn

        object WithApple : LogIn
    }

    //Might be challenged by CAPTCHA
    data class SignUp(val name: String, val email: String, val confirmedPassword: String): IdentityActions

    //Introduce Email class
    data class ResetPassword(val forEmail: String): IdentityActions

    data class VerifyRegistration(val forEmail: String, val token: String): IdentityActions

    //Maybe merge with previous one
    data class SolveChallenge(val solution: String) : IdentityActions

    object LogOut : IdentityActions

    //Or make this feature internal
    object RefreshToken : IdentityActions

    //Or make this feature internal
    object ValidateEmail : IdentityActions

    data class SetPin(val pin: Int) : IdentityActions

    data class AddAuthenticationFactor(val factor: String) : IdentityActions

    //Check out OneTap SDK from Google
    object PickSystemAccount : IdentityActions

    data class ChangePassword(val p: String) : IdentityActions

    object Delete

    //data class AddAuthenticationFactor()
}
