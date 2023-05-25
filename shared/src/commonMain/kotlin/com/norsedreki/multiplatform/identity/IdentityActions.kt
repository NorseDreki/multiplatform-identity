package com.norsedreki.multiplatform.identity

sealed interface IdentityActions {

    sealed interface WithPassword : IdentityActions {

    }

    sealed interface PasswordLess : IdentityActions {

    }

    sealed interface LogIn : IdentityActions {
        data class WithPassword(val email: String, val password: String) : LogIn
    }

    data class SignUp(val name: String, val email: String, val confirmedPassword: String): IdentityActions

    //Introduce Email class
    data class ResetPassword(val forEmail: String): IdentityActions

    data class VerifyRegistration(val forEmail: String, val token: String): IdentityActions

    object LogOut : IdentityActions
}
