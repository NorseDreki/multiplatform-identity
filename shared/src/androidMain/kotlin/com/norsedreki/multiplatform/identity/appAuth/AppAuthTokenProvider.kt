package com.norsedreki.multiplatform.identity.appAuth

import android.content.Context
import android.net.Uri
import com.norsedreki.multiplatform.identity.TokenProvider
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration

class AppAuthTokenProvider(context: Context) : TokenProvider {

    //val d = AuthorizationServiceDiscovery()
    val c = AuthorizationServiceConfiguration(Uri.EMPTY, Uri.EMPTY)
    val a = AuthorizationService(context, AppAuthConfiguration.DEFAULT)

    val st = AuthState()

    init {
        //val d = AuthorizationServiceDiscovery()
        val c = AuthorizationServiceConfiguration(Uri.EMPTY, Uri.EMPTY)
        val a = AuthorizationService(context, AppAuthConfiguration.DEFAULT)
    }

    override fun requestToken(email: String, password: String): String {
        //a.performTokenRequest()

        TODO("Not yet implemented")
    }

    override fun refreshToken() {
        //a.toke

        TODO("Not yet implemented")
    }

    override fun revokeToken(): Boolean {
        //a.performEndSessionRequest()

        TODO("Not yet implemented")
    }
}
