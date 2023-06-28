package com.norsedreki.multiplatform.identity

data class User(
    val email: String,
    val provider: String,
    val credentials: Credentials,
    val pin: Int,
    val enrolledFactors: List<String>
)
