package com.norsedreki.multiplatform.identity

import kotlinx.coroutines.flow.Flow

// When library consumer adds new functionality on top of library's
// Decorator pattern is used. Also Composite patttern compatible
// But how to extend Actions and add new on top, given it's a sealed class?
// And how to extend state, given the same limitation?
class DecoratedIdentity : IdentityContract {

    //private val delegate: Identity = Identity()

    override fun inkove(actions: IdentityActions) {
        //delegate.invoke()
    }

    override val state: Flow<AdtState>
    // delegate.state
        get() = TODO("Not yet implemented")
}

interface IdentityContract {

    fun inkove(actions: IdentityActions)

    val state: Flow<AdtState>
}

// We can actually extend such actions
interface ExtActions : IdentityActions {

    object Dummy : ExtActions
}

interface ExtState : AdtState {


}
