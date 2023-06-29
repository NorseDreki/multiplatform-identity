package com.norsedreki.multiplatform.identity

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class Greeting {
    private val platform: Platform = getPlatform()

    //private val identity = Identity()

    fun greet(): String {
      /*  identity(IdentityActions.LogIn.WithPassword("213", "124"))

        val r = runBlocking {
            identity.state.state.first()
        }
*/
        return "Hello, ${platform.name}!"
    }
}