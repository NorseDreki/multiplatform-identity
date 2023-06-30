package com.norsedreki.multiplatform.ui

import com.norsedreki.multiplatform.identity.AdtState
import com.norsedreki.multiplatform.identity.Identity
import kotlinx.coroutines.flow.onEach

class IdentityNavigator(val identity: Identity) {

    fun a() {
        /*identity.state.state
            .onEach {
                when (it) {
                    AdtState.NotLoggedIn -> LoginScreen()
                    AdtState.LoggedIn -> {}

                }
            }*/
    }
}