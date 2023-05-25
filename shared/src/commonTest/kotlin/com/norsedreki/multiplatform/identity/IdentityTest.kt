package com.norsedreki.multiplatform.identity

import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class IdentityTest {

    @Test
    fun shouldLogoutIfLoggedIn() {
        val sut = Identity()

        sut.logout()

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
            sut.logout()
        }
    }
}
