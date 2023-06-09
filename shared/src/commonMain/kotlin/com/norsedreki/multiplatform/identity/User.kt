package com.norsedreki.multiplatform.identity

import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Serializable
data class User(
    val email: String,
    val provider: String,
    val credentials: Credentials,
    val pin: Int,
    val enrolledFactors: List<String>
)
