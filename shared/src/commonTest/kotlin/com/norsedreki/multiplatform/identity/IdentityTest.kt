package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.AdtState.*
import com.norsedreki.multiplatform.identity.FakeTokenProvider.Companion.ValidUserAEmail
import com.norsedreki.multiplatform.identity.FakeTokenProvider.Companion.ValidUserAPassword
import com.norsedreki.multiplatform.identity.IdentityActions.*
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.mockative.Mock
import io.mockative.classOf
import io.mockative.mock
import io.mockative.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class IdentityTest {

    private lateinit var identity: Identity

    private lateinit var privateState: SerializedPrivateState

    @Mock
    val api = mock(classOf<TokenProvider>())

    @BeforeTest
    fun beforeTest() {
        val ip = FakeTokenProvider()
        privateState = FakeSerializedPrivateState()
        identity = Identity(api, ip, privateState)
    }

    @Test
    fun `should initialize as not logged in`() = runTest {
        val s = identity.state.state.first()

        s.shouldBeTypeOf<NotLoggedIn>()
    }

    @Test
    fun `log in with valid email and password`() = runTest {
        identity(
            LogIn.WithPassword(ValidUserAEmail, ValidUserAPassword)
        )

        val out = identity.state.state.first()

        out.shouldBeTypeOf<LoggedIn>()
        out.email shouldBe ValidUserAEmail
    }

    @Test
    fun `fail to login with invalid credentials`() = runTest {
        identity(
            LogIn.WithPassword("invalidEmail", "invalidPassword")
        )

        // Add Turbine library for catching several states

        val out = identity.state.state.first()
        // OR catch an exception instead?
        out.shouldBeTypeOf<LoginFailed>()
    }

    @Test
    fun `logout with success`() = runTest {
        identity(
            LogIn.WithPassword(ValidUserAEmail, ValidUserAPassword)
        )

        identity(
            LogOut
        )

        val out = identity.state.state.first()
        out.shouldBeTypeOf<NotLoggedIn>()

        privateState.get() shouldBe null

        //verify that local data is cleared up
/*        verify(api)
            .invocation { revokeToken() }
            .wasInvoked()*/
    }

    @Test
    fun `should not emit new value if it's not distinct -- idempotency`() {
        identity(
            LogIn.WithPassword(ValidUserAEmail, ValidUserAPassword)
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
    fun `auto-login if access token is saved locally`() = runTest {
        identity(
            LogIn.WithPassword(ValidUserAEmail, ValidUserAPassword)
        )

        /*identity(
            LogOut
        )*/

        val ip = FakeTokenProvider()
        val newIdentity = Identity(api, ip, privateState)

        val out = newIdentity.state.state.first()
        out.shouldBeTypeOf<LoggedIn>()
        out.email shouldBe ValidUserAEmail
    }

    @Test
    fun `persist and reload feature state`() = runTest {
        identity(
            LogIn.WithPassword(ValidUserAEmail, ValidUserAPassword)
        )

        identity(
            LogOut
        )

        val ip = FakeTokenProvider()
        val newIdentity = Identity(api, ip, privateState)

        val out = newIdentity.state.state.first()
        out.shouldBeTypeOf<NotLoggedIn>()

        //create a feature and login

        // kill / stop using feature

        // create new feature instance and observe restored state
    }

    @Test
    fun `should record logged in analytics event`() = runTest {

    }

    @Test fun `should log debug events for developers`() = runTest {

    }

    @Test
    fun `saved tokens should be encrypted`() {

    }

    @Test
    fun `should cancel all subscriptions upon stop`() {

    }
}
