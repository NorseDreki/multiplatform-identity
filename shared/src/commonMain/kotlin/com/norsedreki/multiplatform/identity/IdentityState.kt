package com.norsedreki.multiplatform.identity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class IdentityState : State {
    override val isLoggedIn: Flow<Boolean>
        get() = TODO("Not yet implemented")
    override val accessToken: Flow<String>
        get() = TODO("Not yet implemented")

    val subscribingScope = CoroutineScope(Dispatchers.Unconfined) //CoroutineScope(SupervisorJob())

    private val stateSubject = MutableStateFlow<AdtState>(AdtState.NotLoggedIn)

    /*init {
        stateSubject = MutableSharedFlow<AdtState>(
            replay = 1,
            // onBufferOverflow = BufferOverflow.DROP_OLDEST,
            // extraBufferCapacity = 1
        )

        val st = MutableStateFlow(1)
        st.

        subscribingScope.launch {
            stateSubject.emit(AdtState.NotLoggedIn)
        }
    }*/

    override val state = stateSubject.asSharedFlow()



    fun update(newState: AdtState) {
        subscribingScope.launch {
            stateSubject.emit(newState)
        }
    }
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
        val email: String, val accessToken: String, val expiresIn: Int, val withProvider: String) : AdtState
}

enum class Challenge {
    EmailCode, PhoneSms, GoogleAuthenticator, Captcha, Password, OTPinPush
}
