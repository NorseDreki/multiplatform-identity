package com.norsedreki.multiplatform.identity.auth0

import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.norsedreki.multiplatform.identity.TokenProvider

class Auth0TokenProvider() : TokenProvider {

    val a = AuthenticationAPIClient(Auth0("", ""))

    override fun requestToken(email: String, password: String): String {
        /*val res = a.login(email, password)
            .validateClaims()
            .await()*/

        return ""
    }

    override fun refreshToken() {
        TODO("Not yet implemented")
    }

    override fun revokeToken(): Boolean {
        return true
        //a.
    }
}