package com.norsedreki.multiplatform.identity

class FakeTokenProvider : IdentityProvider {

    companion object {
        const val ValidUserAEmail = "validUserA"
        const val ValidUserAPassword = "validUserAPassword"

        val ValidCredentials =
            Credentials(
                "Bearer",
                "accessToken",
                "refreshToken",
                "idToken",
                9999999999L,
                "api"
            )

        val RefreshedCredentials =
            Credentials(
                "Bearer",
                "newAccessToken",
                "newRefreshToken",
                "updatedIdToken",
                9999999999L,
                "api"
            )
    }

    private val existingUsers =
        mapOf(
            ValidUserAEmail to ValidUserAPassword
        )

    private val loggedInUsers =
        mutableMapOf(
            ValidUserAEmail to ValidCredentials
        )

    override suspend fun logIn(email: String, password: String): Credentials {
        if (existingUsers.keys.contains(email)) {
            if (existingUsers.values.contains(password)) {
                loggedInUsers[ValidUserAEmail] = ValidCredentials

                return ValidCredentials
            } else {
                throw RuntimeException()
            }
        } else {
            throw RuntimeException()
        }
    }

    override suspend fun signOut(email: String) {
        loggedInUsers.remove(email)
    }

    override suspend fun refreshCredentials(email: String, refreshToken: String): Credentials {
        val currentRefreshToken = loggedInUsers[email]!!.refreshToken

        if (currentRefreshToken == refreshToken) {
            loggedInUsers[email] = RefreshedCredentials
            return RefreshedCredentials
        } else {
            throw RuntimeException()
        }
    }
}
