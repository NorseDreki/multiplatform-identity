package com.norsedreki.multiplatform.identity

interface TokenProvider {

    fun requestToken(email: String, password: String): String

    fun refreshToken()

    //have logout instead (clear cookies, sessions and everything)
    fun revokeToken(): Boolean
}