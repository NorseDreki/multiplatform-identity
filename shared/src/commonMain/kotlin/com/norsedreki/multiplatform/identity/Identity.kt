package com.norsedreki.multiplatform.identity

class Identity(
    tokenProvider: TokenProvider,
    identityProvider: IdentityProvider,
    privateState: SerializedPrivateState = FakeSerializedPrivateState()
) {
    val dispatcher = Dispatcher()
    val state = IdentityState()

    private val eh = IdentityEventHandler(dispatcher, state, tokenProvider, identityProvider, privateState)

    init {
        eh.setup()
    }

    operator fun invoke(action: IdentityActions) {
        //runBlocking {  }

        //coroutineScope {  }

        dispatcher(action)
    }
}
