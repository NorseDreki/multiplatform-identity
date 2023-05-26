package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.IdentityActions.*

class Identity {
    var state: String = "undefined"

    val st = IdentityState()

    //Extract to actions handler
    operator fun invoke(action: IdentityActions) {
        when (action) {
            is LogIn.WithPassword -> loginWithPassword(action)
            LogOut -> logout()
            is ResetPassword -> TODO()
            is SignUp -> TODO()
            is VerifyRegistration -> TODO()
            is AddAuthenticationFactor -> TODO()
            AnonymousLogin -> TODO()
            RefreshToken -> TODO()
            is SetPin -> TODO()
            is SolveChallenge -> TODO()
            ValidateEmail -> TODO()
            PickSystemAccount -> TODO()
        }
    }

    private fun loginWithPassword(action: LogIn.WithPassword) {
        state = "loggedIn"
    }

    fun logout() {
        state = "loggedOut"
    }
}
