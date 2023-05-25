package com.norsedreki.multiplatform.identity

import kotlinx.coroutines.flow.Flow

class IdentityState {


}

interface State {

    val isLoggedIn: Flow<Boolean>

    val accessToken: Flow<String>
}
