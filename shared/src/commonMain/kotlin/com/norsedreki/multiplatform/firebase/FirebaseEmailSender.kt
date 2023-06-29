package com.norsedreki.multiplatform.firebase

import com.norsedreki.multiplatform.identity.EmailSender
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth

class FirebaseEmailSender : EmailSender {
    override fun sendRegistrationConfirmation() {

        //val a : FirebaseUser
        //a.sendEmailVerification()
        TODO("Not yet implemented")
    }

    override fun sendLoginOtp() {
        //Firebase.auth.sendSignInLinkToEmail()
        TODO("Not yet implemented")
    }

    override fun sendResetPassword() {
        //Firebase.auth.sendPasswordResetEmail("email")
        TODO("Not yet implemented")
    }

    override fun sendPasswordChanged() {
        //Firebase.auth.sendSignInLinkToEmail()
        TODO("Not yet implemented")
    }
}