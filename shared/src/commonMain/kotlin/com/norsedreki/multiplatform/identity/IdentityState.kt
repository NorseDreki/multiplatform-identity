package com.norsedreki.multiplatform.identity

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class IdentityState : State {
    override val isLoggedIn: Flow<Boolean>
        get() = TODO("Not yet implemented")
    override val accessToken: Flow<String>
        get() = TODO("Not yet implemented")
    override val state: Flow<AdtState>
        get() = flowOf(AdtState.NotLoggedIn)
}

interface State {

    val isLoggedIn: Flow<Boolean>

    val accessToken: Flow<String>

    val state: Flow<AdtState>
}

sealed interface AdtState {

    object NotLoggedIn : AdtState

    data class LoggedInAnonymously(val assignedId: String, val dummyName: String) : AdtState

    object LoggingOut : AdtState

    object LoggingIn : AdtState

    object SigningIn : AdtState

    data class Error(val e: RuntimeException) : AdtState

    //Including CAPTCHA challenge
    data class AwaitingChallenge(val type: Challenge) : AdtState

    data class LoggedIn(
        val email: String, val accessToken: String, val expiresIn: Int, val withProvider: String)
}

enum class Challenge {
    EmailCode, PhoneSms, GoogleAuthenticator, Captcha, Password, OTPinPush
}
