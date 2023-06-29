package com.norsedreki.multiplatform.firebase

import com.norsedreki.multiplatform.identity.TokenProvider
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.OAuthProvider
import dev.gitlive.firebase.auth.PhoneAuthProvider
import dev.gitlive.firebase.auth.auth

class FirebaseTokenProvider() : TokenProvider {
    override fun requestToken(email: String, password: String): String {
        //Firebase.auth.currentUser!!.
        return ""

        //FirebaseAuth()
    }

    override fun refreshToken() {
        //OAuthProvider.credential()
        //PhoneAuthProvider.
        //Firebase.auth.
        TODO("Not yet implemented")
    }

    override fun revokeToken(): Boolean {
        //val a = Firebase.auth.signOut()
        TODO("Not yet implemented")
    }
}