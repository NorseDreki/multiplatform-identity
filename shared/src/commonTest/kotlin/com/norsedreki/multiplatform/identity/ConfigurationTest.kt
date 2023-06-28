package com.norsedreki.multiplatform.identity

class ConfigurationTest {

    val features = listOf(
        "anonymousLogin",
        "registration",
        "changePassword",
        "resetPassword",
        "oidc",
        "emailValidation", // and password
        "biometrics",
        "secondFactors:",
        "refreshToken",
        "providers:"
    )
}