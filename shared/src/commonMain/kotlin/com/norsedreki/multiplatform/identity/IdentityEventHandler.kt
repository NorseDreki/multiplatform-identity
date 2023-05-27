package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.IdentityActions.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.subscribe

class IdentityEventHandler
internal constructor(
    private val dispatcher: Dispatcher,
    private val state: IdentityState
) : EventsHandler<IdentityActions> {

    override fun setup() {
        println("Set up handler $this")
        val job = dispatcher
            .ofType<IdentityActions>()
            .onEach {
                println("Got event $it")
                when (it) {
                    is LogIn.WithPassword -> loginWithPassword(it)
                    is AddAuthenticationFactor -> TODO()
                    AnonymousLogin -> {
                        println("Anon login")
                    }
                    LogOut -> logout()
                    PickSystemAccount -> TODO()
                    RefreshToken -> TODO()
                    is ResetPassword -> TODO()
                    is SetPin -> TODO()
                    is SignUp -> TODO()
                    is SolveChallenge -> TODO()
                    ValidateEmail -> TODO()
                    is VerifyRegistration -> TODO()
                    is ChangePassword -> TODO()
                }
            }
            .catch { println("11111 $it") }
            .launchIn(dispatcher.subscribingScope)
    }

    private fun loginWithPassword(action: LogIn.WithPassword) {
        state.update(AdtState.LoggedIn("111", "111", 0, "222"))
        println("State = $state")
    }

    private fun logout() {
        state.update(AdtState.NotLoggedIn)
    }
}
