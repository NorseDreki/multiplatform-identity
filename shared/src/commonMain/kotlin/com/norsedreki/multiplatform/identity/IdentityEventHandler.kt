package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.IdentityActions.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class IdentityEventHandler
internal constructor(
    private val dispatcher: Dispatcher,
    private val state: IdentityState,
    private val tokenProvider: TokenProvider,
    val identityProvider: IdentityProvider,
    val privateState: SerializedPrivateState = FakeSerializedPrivateState()
    //val analytics:
) : EventsHandler<IdentityActions> {

    override fun setup() {

        state.subscribingScope.launch {

            val u = privateState.get()

            if (u != null) {
                state.stateSubject.value = //AdtState.NotLoggedIn
                    AdtState.LoggedIn(
                        u.email,
                        u.credentials.accessToken,
                        0,
                        "222",
                        1111,
                        emptyList(),
                        ""
                    )
            }
        }

        //if (secureStorage.get()) {
            //construct state with default value of loggedIn
        //}

        //if (privateState.get() != null)


        println("Set up handler $this")
        val job = dispatcher
                // take until
            .ofType<IdentityActions>()
            .onEach {
                println("Got event $it")
                when (it) {
                    is LogIn.WithPassword -> loginWithPassword(it)
                    is AddAuthenticationFactor -> TODO()
                    AnonymousLogin -> anonymousLogin(it)
                    LogOut -> logout()
                    PickSystemAccount -> TODO()
                    RefreshToken -> TODO()
                    is ResetPassword -> TODO()
                    is SetPin -> setPin(it)
                    is SignUp -> TODO()
                    is SolveChallenge -> solveChallenge(it)
                    ValidateEmail -> TODO()
                    is VerifyRegistration -> TODO()
                    is ChangePassword -> TODO()
                    LogIn.WithApple -> TODO()
                    LogIn.WithGoogle -> TODO()
                    PasswordLess.Passkey -> TODO()
                }
            }
            .catch { println("11111 $it") }
            .launchIn(dispatcher.subscribingScope)
    }

    private fun anonymousLogin(it: IdentityActions) {
        //check feature is available at runtime, in supported features list

        TODO("Not yet implemented")
    }

    private fun solveChallenge(action: IdentityActions.SolveChallenge) {
        //when (action.) {

        //}

    }

    private fun setPin(action: SetPin) {
        if (state.stateSubject.value !is AdtState.LoggedIn) {
            state.nextState(AdtState.Error(RuntimeException()))
        }

        //Restart

        //setTimer() {
        //
        // }
        //state.nextState(Challenge.UserPin)

        //state.updateWith(action.pin)

        TODO("Not yet implemented")
    }

    private fun loginWithPassword(action: LogIn.WithPassword) {
        state.subscribingScope.launch {
            try {
                //val accessToken = tokenProvider.requestToken(action.email, action.password)
                val c = identityProvider.logIn(action.email, action.password)

                val u = User(action.email, "email", c, 1111, emptyList())


                privateState.put(u)
                //secureStorage.put()

                state.nextState(
                    AdtState.LoggedIn(
                        action.email,
                        c.accessToken,
                        0,
                        "222",
                        1111,
                        emptyList(),
                        ""
                    )
                )
            } catch (e: RuntimeException) {
                state.nextState(AdtState.LoginFailed(RuntimeException()))
                //state.nextState(AdtState.NotLoggedIn)
            }

            println("State = $state")
        }
    }

    private fun logout() {
        state.subscribingScope.launch {
            //state.nextState(AdtState.LoggingOut)
            try {
                privateState.clear()

                //tokenProvider.revokeToken()
                state.nextState(AdtState.NotLoggedIn)
            } catch (e: RuntimeException) {
                state.nextState(AdtState.LogoutFailed(RuntimeException()))
                //state.nextState(AdtState.NotLoggedIn)
            }
        }
    }
}
