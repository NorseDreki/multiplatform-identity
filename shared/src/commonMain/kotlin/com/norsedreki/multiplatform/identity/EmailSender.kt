package com.norsedreki.multiplatform.identity

interface EmailSender {

    fun sendRegistrationConfirmation()

    fun sendLoginOtp()

    fun sendResetPassword()

    fun sendPasswordChanged()
}