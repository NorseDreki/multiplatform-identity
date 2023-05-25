package com.norsedreki.multiplatform.identity

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform