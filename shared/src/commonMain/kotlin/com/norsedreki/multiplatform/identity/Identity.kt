package com.norsedreki.multiplatform.identity

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class Identity {
    val dispatcher = Dispatcher()
    val state = IdentityState()

    private val eh = IdentityEventHandler(dispatcher, state)

    init {
        eh.setup()
    }

    operator fun invoke(action: IdentityActions) {
        runBlocking {  }

        //coroutineScope {  }

        dispatcher(action)
    }
}
