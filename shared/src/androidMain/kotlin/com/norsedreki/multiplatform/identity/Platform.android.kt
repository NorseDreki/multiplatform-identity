package com.norsedreki.multiplatform.identity

import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

fun abc() {
    val a = AuthenticationAPIClient(Auth0("", ""))

    Napier.v("123123")

    Napier.base(DebugAntilog())
    //a.lo
}