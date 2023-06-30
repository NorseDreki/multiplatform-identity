package com.norsedreki.multiplatform.identity

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class IdentityState : State {
    override val isLoggedIn: Flow<Boolean>
        get() = TODO("Not yet implemented")
    override val accessToken: Flow<String>
        get() = TODO("Not yet implemented")

    val subscribingScope = CoroutineScope(Dispatchers.Unconfined) //CoroutineScope(SupervisorJob())

    internal val stateSubject = MutableStateFlow<AdtState>(AdtState.NotLoggedIn)

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


    fun nextState(newState: AdtState) {
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

    data class Pending(val what: String) : AdtState

    data class Error(val e: RuntimeException) : AdtState

    data class LoginFailed(val e: RuntimeException) : AdtState

    data class LogoutFailed(val e: RuntimeException) : AdtState

    //Including CAPTCHA challenge
    data class PendingChallenge(val type: Challenge) : AdtState

    //PASSWORD POLICY NOT MET

    //EMAIL POLICY NOT MET

    //	"token_type": "Bearer",
    //	"access_token": "ya29.a0ARrdaM8ZKzg-2OB3_4Nv_WHzrETuFc6RK1bnIP9kmveRG5VNrE7-eYg3XPEnb3J66OSZwqjhlL-sUerY83TwBesMyaY4wrFnJXWgginXs9E661yZaWtjeE1mj5N72XDCWvhy5gxVyahlgH0X7tOj12EWauic",
    //	"expires_at": 1638485069411,
    //	"id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjkzNDFhYmM0MDkyYjZmYzAzOGU0MDNjOTEwMjJkZDNlNDQ1MzliNTYiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxMDgwMjIwMjgwMDc5LWQwYzBiYTEwNzZrZHNyM2QwcGJ1dmQ3ZTNucGoxa2V0LmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiMTA4MDIyMDI4MDA3OS1kMGMwYmExMDc2a2RzcjNkMHBidXZkN2UzbnBqMWtldC5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjExMzA1OTQ4OTgwNjc5NjM3Mjc1MiIsImVtYWlsIjoicHRydWl6LmRlbW9AZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJxb0pVZUQ5cWswb2ZzZFd4WGNQTU53Iiwibm9uY2UiOiJfVVlmbkk5QnVvRGlXWk1ULVJJaGJRIiwibmFtZSI6IlBhdWwgUnVpeiIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQVRYQUp4THlidm5aWERZcks4em0yWGdsT1NxdF9hbk1tU1AxX3ZxWGJ2cj1zOTYtYyIsImdpdmVuX25hbWUiOiJQYXVsIiwiZmFtaWx5X25hbWUiOiJSdWl6IiwibG9jYWxlIjoiZW4iLCJpYXQiOjE2Mzg0ODE0NjksImV4cCI6MTYzODQ4NTA2OX0.gzOHM9By6qkX6GtNymkqh3xC55jaCFqpUDOY_5W4pWLm8oD665Nz0cC7k3mwB3xADxkRPWTXnQIUlMJPS3Rci1cyiHtNgnXsX8Chr0winnu0UYcQXZAHY01fwK5F4_ya86k8B3QsSPyala3JwITuk2khkhje6Uw9UOjzyg-L0IorQbRo_11lEjykPzVX4WTsPDnIYwDVvZjRiEkVREf5DeGGNjnucypRzHQVBa8zMLfHyNC5i1RYeaHTR3ShSblLQLxIJFsumJUqlQjr1TAjE9WZvIq3pbNr-ynHpJHi4Ui-o7zWBTiHskpZzrKHFkXBC82T5pW-6slXP9wX9BUtRg",
    //	"refresh_token": "1\/\/04_kKKzI1t7l3CgYIARAAGAQSNwF-L9IrEvzz3hRxVZkB4QSSzJuszUNVvx2NTHaZfwxhtpF_UcC1nxS958r0wdJsEqHGFKv3mnQ",
    //	"scope": "https:\/\/www.googleapis.com\/auth\/userinfo.email https:\/\/www.googleapis.com\/auth\/userinfo.profile https:\/\/www.googleapis.com\/auth\/drive.file openid",
    //	"additionalParameters": {}

    data class LoggedIn(
        val email: String,
        val accessToken: String,
        val expiresIn: Int,
        val withProvider: String,
        val protectionPin: Int,
        val secondFactors: List<String>,
        val user: String //got this from ID token
    ) : AdtState
}

data class Pin(val value: Int) {
    companion object {
        const val NOT_SET = ""
    }
}

enum class Challenge {
    UserPin, EmailCode, PhoneSms, GoogleAuthenticator, Captcha, Password, OTPinPush
}
