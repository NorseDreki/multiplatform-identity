package com.norsedreki.multiplatform.identity

data class Credentials(
    val tokenType: String,
    val accessToken: String,
    val refreshToken: String,
    val idToken: String,
    val expiresAt: Long,
    val scope: String
)