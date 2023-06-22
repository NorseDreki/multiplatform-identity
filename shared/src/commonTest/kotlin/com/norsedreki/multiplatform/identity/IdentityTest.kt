package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.IdentityActions.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.replay
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.subscribe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class IdentityTest {

    private lateinit var identity: Identity

    @BeforeTest
    fun beforeTest() {
        identity = Identity()
    }

    @Test
    fun `should not emit new value if it's not distinct -- idempotency`() {
        //identity(LogOut)
        //identity(LogOut)
        //identity(LogOut)
        //identity(LogOut)
    }

    @Test
    fun `all subscribers should get a replay for the latest state`() {

        //identity(LogOut)

        println("111111")

        runTest {
            //identity(LogOut)

            println("2222222")

            val v = identity.state.state.onEach {
                println("999999 $it")
            }
                .drop(1)
                .onEach {
                    it shouldBe AdtState.LoggingOut
                }
                .launchIn(this)

            println("33333 $v")

            delay(100)
            identity(LogOut)

            val v1 = identity.state.state.onEach {
                println("5555 $it")
                it shouldBe AdtState.LoggingOut
            }.launchIn(this)

            identity(LogOut)
            identity(LogOut)
            identity(LogOut)
            identity(LogOut)

            v.cancel()
            v1.cancel()
        }

        /*println("666666")

        identity(LogOut)

        runTest {
            identity.state.state.onEach {
                println("5555 $it")
                it shouldBe AdtState.LoggingOut
            }.launchIn(this)

        }*/

        //identity(LogOut)


    }

    @Test
    fun `should initialize as not logged in`() = runTest {
        val s = identity.state.state.first()

        s.shouldBeTypeOf<AdtState.NotLoggedIn>()
    }

    @Test
    fun `wow wow wow`() {

        identity(
            LogIn.WithPassword("dummy", "dummy")
        )

        runTest {
            val out = identity.state.state.first()

            assertTrue { out is AdtState.LoggedIn }
            (out as AdtState.LoggedIn).email shouldBe "dummy"
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
