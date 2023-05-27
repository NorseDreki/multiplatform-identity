package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.IdentityActions.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Identity {
    var state: String = "undefined"

    val st = IdentityState()

    val dispatcher = Dispatcher()

    val eh = IdentityEventHandler(dispatcher, state)

    init {
        eh.setup()
    }

    fun start() {
        GlobalScope.launch {
            //Log.d(TAG, Thread.currentThread().name.toString())
        }
        //Log.d("Outside Global Scope", Thread.currentThread().name.toString())
    }

    //Extract to actions handler
    operator fun invoke(action: IdentityActions) {
        dispatcher(action)

        /*when (action) {
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
        }*/
    }

    private fun loginWithPassword(action: LogIn.WithPassword) {
        state = "loggedIn"
    }

    fun logout() {
        state = "loggedOut"
    }
}
