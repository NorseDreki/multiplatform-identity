package com.norsedreki.multiplatform.identity

class Identity {
    val dispatcher = Dispatcher()
    val state = IdentityState()

    private val eh = IdentityEventHandler(dispatcher, state)

    init {
        eh.setup()
    }

    operator fun invoke(action: IdentityActions) {
        dispatcher(action)
    }
}
