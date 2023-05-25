package com.norsedreki.multiplatform.identity

import com.norsedreki.multiplatform.identity.IdentityActions.*
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class IdentityTest {

    @Test
    fun shouldLoginWithCorrectEmailPassword() {
        val identity = Identity()

        identity(
            LogIn.WithPassword("dummy", "dummy")
        )

        assertTrue { identity.state == "loggedIn" }
    }

    @Test
    fun shouldNotLoginWithIncorrectEmailPassword() {
        val identity = Identity()

        assertFailsWith(
            RuntimeException::class,
            "Expected an exception if loggind out w/o logging in"
        ) {
            identity(
                LogIn.WithPassword("incorrect", "incorrect")
            )
        }
    }

    @Test
    fun shouldLogoutIfLoggedIn() {
        val sut = Identity()

        sut(LogOut)

        val state = sut.state

        assertTrue("Expected being logged out after logging in") { state == "loggedOut" }
    }

    @Test
    fun shouldThrowExceptionForLogoutIfNotLoggedIn() {
        val sut = Identity()

        assertFailsWith(
            RuntimeException::class,
            "Expected an exception if loggind out w/o logging in"
        ) {
            sut(LogOut)
        }
    }
}
