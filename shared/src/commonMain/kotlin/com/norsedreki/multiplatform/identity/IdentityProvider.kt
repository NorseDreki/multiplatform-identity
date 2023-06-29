package com.norsedreki.multiplatform.identity

interface IdentityProvider {

    suspend fun logIn(email: String, password: String): Credentials

    suspend fun refreshCredentials(email: String, refreshToken: String): Credentials

    suspend fun signOut(email: String)
}