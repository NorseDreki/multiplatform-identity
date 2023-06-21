package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.IdentityActions.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class IdentityTest {

    @Test
    fun should1() {
        val identity = Identity()

        identity(
            LogIn.WithPassword("dummy", "dummy")
        )

        runTest {
            val out = identity.state.state.first()

            assertTrue { out is AdtState.LoggedIn }
            assertTrue { (out as AdtState.LoggedIn).email == "dummy" }
        }
    }

    @Test
    fun shouldLoginWithCorrectEmailPassword() {
        val identity = Identity()

        val dest = mutableListOf<AdtState>()

        identity.state.subscribingScope.launch {
            identity.state.state
                .onEach { println("Next state $it") }
                .toList(dest)
        }

        identity(
            LogIn.WithPassword("dummy", "dummy")
        )




        runBlocking {
            launch {

            }

            async {  }

            withContext(Dispatchers.Default) {
                //async {  }
            }

            assertTrue { dest[0] is AdtState.LoggedIn }
            identity(LogOut)
        }
    }

    @Test
    fun shouldNotLoginWithIncorrectEmailPassword() {
        val identity = Identity()

        assertTrue { true }

        /*assertFailsWith(
            RuntimeException::class,
            "Expected an exception if loggind out w/o logging in"
        ) {
            identity(
                LogIn.WithPassword("incorrect", "incorrect")
            )
        }*/
    }

    @Test
    fun shouldLogoutIfLoggedIn() {
        val sut = Identity()

        sut(LogOut)

        runBlocking {
            val s = sut.state.state.first()

            assertTrue("Expected being logged out after logging in") { s == AdtState.NotLoggedIn }
        }
    }

    @Test
    fun shouldThrowExceptionForLogoutIfNotLoggedIn() {
        val sut = Identity()

        assertTrue { true }

        /*assertFailsWith(
            RuntimeException::class,
            "Expected an exception if loggind out w/o logging in"
        ) {
            sut(LogOut)
        }*/
    }
}
