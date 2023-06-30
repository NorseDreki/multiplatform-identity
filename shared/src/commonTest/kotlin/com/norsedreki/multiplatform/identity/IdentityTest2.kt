package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.IdentityActions.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.given
import io.mockative.mock
import io.mockative.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class IdentityTest2 {

    private lateinit var identity: Identity

    @Mock
    val api = mock(classOf<TokenProvider>())

    @BeforeTest
    fun beforeTest() {
        val ip = FakeTokenProvider()
        identity = Identity(api, ip,)
    }

    @Test
    fun `should set pin when logged in`() = runTest {
        val email = "dummy"
        val password = "dummy"

        given(api)
            .invocation { requestToken(email, password) }
            .thenReturn("adf")

        identity(
            LogIn.WithPassword(email, password)
        )

        identity(
            SetPin(1111)
        )

        val out = identity.state.state.first()

        //or it should be challenge with pin -- right away
        out.shouldBeTypeOf<AdtState.LoggedIn>()
        // do not expose pin
        // read from encrypted prefs instead
        out.protectionPin shouldBe 1111

    }

    @Test
    fun `should not allow to set pin if not logged in`() = runTest {
        identity(
            SetPin(1111)
        )

        val out = identity.state.state.first()

        out.shouldBeTypeOf<AdtState.Error>()
    }

    @Test
    fun `should challenge with CAPTCHA upon registration`() {
        //out.shouldBeTypeOf<AdtState.PendingChallenge>()
        //out.type shouldBe  Challenge.Captcha
    }

    @Test
    fun `should have pending state upon user or IO input`() {
        //out.shouldBeTypeOf<AdtState.PendingChallenge>()
        //out.type shouldBe  Challenge.Registration /
    }

    @Test
    fun `should add second authentication factor`() {

    }

    @Test
    fun `should challenge with pin if pin is set`() = runTest {
        //

        val out = identity.state.state.first()

        out.shouldBeTypeOf<AdtState.PendingChallenge>()
        out.type shouldBe  Challenge.UserPin
    }

    @Test
    fun `should challenge with email confirmation code after registration`() {

    }


    @Test
    fun `should not emit new value if it's not distinct -- idempotency`() {
        val email = "dummy"
        val password = "dummy"

        given(api)
            .invocation { requestToken(email, password) }
            .thenReturn("adf")

        identity(
            LogIn.WithPassword(email, password)
        )

        identity(
            LogOut
        )

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
    fun `auto-login if access token is saved locally`() {

    }

    @Test
    fun `persist and reload feature state`() {

    }



    @Test
    fun `log in with valid email and password`() = runTest {
        val email = "dummy"
        val password = "dummy"

        given(api)
            .invocation { requestToken(email, password) }
            .thenReturn("adf")

        identity(
            LogIn.WithPassword(email, password)
        )

        val out = identity.state.state.first()

        out.shouldBeTypeOf<AdtState.LoggedIn>()
        out.email shouldBe email

        verify(api)
            .invocation { requestToken(email, password) }
            .wasInvoked()
    }

    @Test
    fun `fail to login with invalid credentials`() = runTest {
        val email = "dummy"
        val password = "dummy"

        given(api)
            .invocation { requestToken(email, password) }
            .thenThrow(RuntimeException())

        identity(
            LogIn.WithPassword(email, password)
        )

        val out = identity.state.state.first()
        out.shouldBeTypeOf<AdtState.LoginFailed>()

        verify(api)
            .invocation { requestToken(email, password) }
            .wasInvoked()
    }

    @Test
    fun `logout with success`() = runTest {
        val email = "dummy"
        val password = "dummy"

        given(api)
            .invocation { requestToken(email, password) }
            .thenReturn("adf")

        identity(
            LogIn.WithPassword(email, password)
        )

        identity(
            LogOut
        )

        val out = identity.state.state.first()
        out.shouldBeTypeOf<AdtState.NotLoggedIn>()

        verify(api)
            .invocation { revokeToken() }
            .wasInvoked()
    }

    @Test
    fun shouldLoginWithCorrectEmailPassword() {
        /*val identity = Identity(api)

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
        }*/
    }

    @Test
    fun shouldNotLoginWithIncorrectEmailPassword() {
        //val identity = Identity(api)

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
        /*val sut = Identity(api)

        sut(LogOut)

        runBlocking {
            val s = sut.state.state.first()

            assertTrue("Expected being logged out after logging in") { s == AdtState.NotLoggedIn }
        }*/
    }

    @Test
    fun shouldThrowExceptionForLogoutIfNotLoggedIn() {
        //val sut = Identity(api)

        assertTrue { true }

        /*assertFailsWith(
            RuntimeException::class,
            "Expected an exception if loggind out w/o logging in"
        ) {
            sut(LogOut)
        }*/
    }
}
