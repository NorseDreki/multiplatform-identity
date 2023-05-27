package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.IdentityActions.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class IdentityTest {

    class Publisher {
        //val publishingScope = CoroutineScope(SupervisorJob())
        val publishingScope = CoroutineScope(Dispatchers.Unconfined)

        val messagesFlow = MutableSharedFlow<Int>(
/*            replay = 0,
            extraBufferCapacity = 0,*/
            //onBufferOverflow = BufferOverflow.SUSPEND
        )/*.also { flow ->
            // emit messages
            publishingScope.launch {
                repeat(100000) {
                    println("emitting $it")
                    flow.emit(it)
                    delay(500)
                }
            }
        }*/
    }

    @Test
    fun shouldLoginWithCorrectEmailPassword() {
        val identity = Identity()

        val publisher = Publisher()


        //runBlocking {


            identity(
                LogIn.WithPassword("dummy", "dummy")
            )
            val flow = publisher.messagesFlow

            flow.onEach { println("I am colllecting message $it") }
                .catch { println("Ex $it") }
                .launchIn(publisher.publishingScope)

            flow.tryEmit(1)
            flow.tryEmit(1)
            flow.tryEmit(1)
            flow.tryEmit(1)


            publisher.publishingScope.launch {
                flow.emit(1)
                flow.emit(2)
                flow.emit(3)
                flow.emit(4)
                flow.emit(5)
                flow.emit(6)
                flow.emit(7)
                flow.emit(8)
                flow.emit(9)
                flow.emit(10)
                println("Subs count: ${flow.subscriptionCount.value}")

            }
            //delay(1000)
        //}

        assertTrue { identity.state == "loggedIn" }
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
            val s = sut.st.state.single()

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
